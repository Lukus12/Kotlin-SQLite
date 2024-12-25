package com.example.kotlin_sqlite.firebaseClient


import com.example.kotlin_sqlite.domain.models.DataRecordModel
import com.example.kotlin_sqlite.domain.models.PetsClient
import com.example.kotlin_sqlite.utils.FirebaseFieldNames.BIRTH
import com.example.kotlin_sqlite.utils.FirebaseFieldNames.CLIENT
import com.example.kotlin_sqlite.utils.FirebaseFieldNames.DIST
import com.example.kotlin_sqlite.utils.FirebaseFieldNames.GENDER
import com.example.kotlin_sqlite.utils.FirebaseFieldNames.NAME
import com.example.kotlin_sqlite.utils.FirebaseFieldNames.PASSWORD
import com.example.kotlin_sqlite.utils.FirebaseFieldNames.PETS
import com.example.kotlin_sqlite.utils.FirebaseFieldNames.STATUS
import com.example.kotlin_sqlite.domain.models.RecordStatus
import com.example.kotlin_sqlite.utils.FirebaseFieldNames.DATE
import com.example.kotlin_sqlite.utils.FirebaseFieldNames.DOCTOR
import com.example.kotlin_sqlite.utils.FirebaseFieldNames.RECORD
import com.example.kotlin_sqlite.utils.FirebaseFieldNames.TIME
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor(
    private val dbRef: DatabaseReference, //ссылка на нашу бд
    private val gson: Gson
) {
    private var currentUserName:String?=null //введённое пользователем имя
    private fun setUserName(username: String){
        this.currentUserName = username
    }

    fun getUsername(done: (Boolean, String?) -> Unit) {
        if(this.currentUserName!=null){
            done(true, this.currentUserName)
        }
        else{
            done(false, "Ошибка получения имени пользователя")
        }
    }


    fun login(username: String, password: String, done: (Boolean, String?) -> Unit) {
        /* при каждом входе в систему мы проверяем,
          если ли такое имя пользователя, если да -> чекаем пароль */
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //проверка существования пользователя
                //snapshot - бд
                if(snapshot.child(CLIENT).hasChild(username)){
                    //пользователь существует
                    val dbPassword = snapshot.child(CLIENT).child(username).child(PASSWORD).value
                    // Проверяем статус пользователя
                    /*val dbUserStatus = snapshot.child(username).child(STATUS).value

                    if (dbUserStatus == "ONLINE") {
                        // пользователь уже в сети
                        done(false, "Пользователь $username уже в сети")
                        return
                    }*/

                    if(password == dbPassword){
                        //вход
                        dbRef.child(CLIENT).child(username).child(STATUS).setValue("ONLINE")
                            .addOnCompleteListener {
                                setUserName(username)
                                //слушатель завершения
                                done(true,null)
                            }.addOnFailureListener{
                                //слушатель ошибки
                                done(false,"${it.message}")
                            }
                    }
                    else{
                        //сообщение об ошибке
                        done(false,"Неверный пароль")
                    }
                }
                else{
                    done(false,"Пользователя $username не существует")
                }
            }
            override fun onCancelled(error: DatabaseError) {} //обработчик ошибок для логирования
        })
    }

    fun reg(username: String, password: String, done: (Boolean, String?) -> Unit){
        dbRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChild(username)){
                    done(false, "Такой пользователь уже существует")
                }
                else{
                    //регаем нового пользователя, если имени пользователя нет
                    dbRef.child(CLIENT).child(username).child(PASSWORD).setValue(password)
                        .addOnCompleteListener{//если всё успешно, обновляем статус user-а
                            dbRef.child(CLIENT).child(username).child(STATUS).setValue("ONLINE")
                                .addOnCompleteListener {
                                    setUserName(username)
                                    done(true,null)
                                }.addOnFailureListener {
                                    done(false,"${it.message}")
                                }
                        }.addOnFailureListener{
                            done(false,"${it.message}")
                        }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun getPets(username: String, done: (Boolean, List<PetsClient>?) -> Unit) {
        dbRef.child(CLIENT).child(username).child(PETS).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val petsList = mutableListOf<PetsClient>()
                for (petSnapshot in snapshot.children) {
                    val pet = petSnapshot.getValue(PetsClient::class.java)
                    pet?.let { petsList.add(it) }
                }
                done(true, petsList)
            }
            override fun onCancelled(error: DatabaseError) {
                done(false, null) // Обработка ошибок
            }
        })
    }

    fun addPets(username: String, petname: String, petdist: String, petdate: String, petgen: String, done: (Boolean, String?) -> Unit) {
        dbRef.child(CLIENT).child(username).child(PETS).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(petname)) {
                    done(false, "Такой питомец у вас уже есть")
                } else {
                    val petData = mapOf(
                        BIRTH to petdate,
                        DIST to petdist,
                        GENDER to petgen,
                        NAME to petname
                    )

                    dbRef.child(CLIENT).child(username).child(PETS).child(petname).setValue(petData)
                        .addOnCompleteListener {
                            done(true, null)
                        }
                        .addOnFailureListener {
                            done(false, "Ошибка при добавлении питомца")
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                done(false, null)
            }

        })
    }

    fun addRecord(username: String, petname: String, doctor: String, date: String, time: String, done: (Boolean, String?) -> Unit) {
        dbRef.child(CLIENT).child(username).child(RECORD).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var hasActiveRecord = false

                // Перебираем все записи в snapshot
                for (recordSnapshot in snapshot.children) {
                    val status = recordSnapshot.child(STATUS).getValue(RecordStatus::class.java)
                    if (status != null && status != RecordStatus.COMPLETED) {
                        hasActiveRecord = true
                        break
                    }
                }

                // Если есть хотя бы одна активная запись
                if (hasActiveRecord) {
                    done(false, "У вас уже есть активная запись")
                } else {
                    val recordData = mapOf(
                        NAME to petname,
                        DOCTOR to doctor,
                        DATE to date,
                        TIME to time,
                        STATUS to RecordStatus.WAITING
                    )

                    dbRef.child(CLIENT).child(username).child(RECORD).child(date).setValue(recordData)
                        .addOnCompleteListener {
                            done(true, null)
                        }
                        .addOnFailureListener {
                            done(false, "Ошибка при добавлении записи")
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                done(false, null)
            }

        })
    }

    fun getRecords(username: String, done: (Boolean, List<DataRecordModel>?) -> Unit) {
        dbRef.child(CLIENT).child(username).child(RECORD).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val recordList = mutableListOf<DataRecordModel>()
                for (recordSnapshot in snapshot.children) {
                    val pet = recordSnapshot.getValue(DataRecordModel::class.java)
                    pet?.let { recordList.add(it) }
                }
                done(true, recordList)
            }
            override fun onCancelled(error: DatabaseError) {
                done(false, null) // Обработка ошибок
            }
        })
    }


    fun observeRecordStatus(status: (List<DataRecordModel>) -> Unit) {
        dbRef.child(CLIENT).child(currentUserName!!).child(RECORD).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Получаем список записей с их статусами
                val list = snapshot.children.mapNotNull { recordSnapshot ->
                    val name = recordSnapshot.child("name").value?.toString() // Получаем имя питомца
                    val doctor = recordSnapshot.child("doctor").value?.toString() // Получаем имя врача (если нужно)
                    val date = recordSnapshot.child("date").value?.toString() // Получаем дату записи
                    val time = recordSnapshot.child("time").value?.toString() // Получаем время записи (если нужно)
                    val status = recordSnapshot.child(STATUS).value?.toString() // Получаем статус

                    // Создаем объект DataRecordModel
                    if (name != null && status != null) {
                        DataRecordModel(name, doctor, date, time, status)
                    } else {
                        null // Пропускаем записи без имени или статуса
                    }
                }
                status(list) // Вызов колбека с обновленным списком
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    interface Listener{
        fun onLatestStatus(event:DataRecordModel)
    }

    fun changeRecordStatus(status: RecordStatus, date: String) {
        dbRef.child(CLIENT).child(currentUserName!!).child(RECORD).child(date).setValue(status.name)
    }



}
