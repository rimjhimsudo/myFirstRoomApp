package com.example.myroomsampleapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myroomsampleapp.model.Person;

import java.util.List;

@Dao
public interface PersonDao {

    @Query("Select * from person ORDER BY ID")
    List<Person> loadAllPersons();
    @Insert
    void  insertPerson(Person person);
    @Update
    void updatePerson(Person person);
    @Delete
    void deletePerson(Person person);
    @Query("SELECT * FROM PERSON WHERE id= :id ")
    Person loadPersonByID(int id);
}
