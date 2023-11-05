    package com.example.practic221

    import com.example.practic221.adapters.UserdataAdapter
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import androidx.appcompat.app.AlertDialog
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.example.practic221.adapters.ConversionsAdapter
    import com.example.practic221.databinding.ActivityDatabaseDataBinding
    import com.example.practic221.entities.UserdataEntity
    import com.example.practic221.room_database.AppDatabase
    import com.example.practic221.room_database.DatabaseRepository
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.GlobalScope
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.withContext

    class DatabaseDataActivity : AppCompatActivity(), UserdataAdapter.OnItemClickListener  {
        private lateinit var binding:ActivityDatabaseDataBinding
        private lateinit var userDataAdapter: UserdataAdapter
        private lateinit var conversionsAdapter: ConversionsAdapter
        private lateinit var repository: DatabaseRepository
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityDatabaseDataBinding.inflate(layoutInflater)
            setContentView(binding.root)

            repository = DatabaseRepository(AppDatabase.getDatabase(this),this)

            binding.userdataRecyclerview.layoutManager= LinearLayoutManager(this)
            binding.conversionsRecyclerview.layoutManager=LinearLayoutManager(this)

            conversionsAdapter = ConversionsAdapter()
            userDataAdapter = UserdataAdapter(this,this)

            binding.userdataRecyclerview.adapter = userDataAdapter
            binding.conversionsRecyclerview.adapter = conversionsAdapter

            setListsInAdapters()
        }

        private fun setListsInAdapters(){
            GlobalScope.launch(Dispatchers.IO) {
                val userList= repository.getAllUsers()
                val conversionsList = repository.getAllConversions()
                withContext(Dispatchers.Main){
                    userDataAdapter.setUserData(userList)
                    conversionsAdapter.setConversions((conversionsList))

                }
            }
        }
        override fun onItemClicked(userData: UserdataEntity) {
            // Показать список действий, например, в виде диалогового окна или PopupMenu.
            showActionsDialog(userData)
        }

        private fun showActionsDialog(userData: UserdataEntity) {
            // Создайте диалоговое окно или PopupMenu для показа списка действий.
            // Пример:
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Выберите действие")
            builder.setItems(arrayOf("Удалить элемент", "Редактировать элемент")) { _, which ->
                // Обработка выбора действия.
                when (which) {
                    0 -> {
                        // Действие "Удалить элемент"
                        GlobalScope.launch(Dispatchers.IO) {
                            repository.deleteUser(userData)
                        }
                        runOnUiThread {
                            binding.userdataRecyclerview.adapter = UserdataAdapter(this,this)
                        }
                    }
                    1 -> {
                        // Действие "Редактировать элемент"
                        // Ваш код для редактирования элемента
                    }
                }
            }
            builder.show()
        }

    }