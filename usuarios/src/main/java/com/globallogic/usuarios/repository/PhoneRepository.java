package com.globallogic.usuarios.repository;

import java.util.List;
import java.util.UUID;
import com.globallogic.usuarios.entities.Phone;
import com.globallogic.usuarios.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PhoneRepository extends JpaRepository<Phone,UUID>{

    public List <Phone> findAllByUser(User userId);
}