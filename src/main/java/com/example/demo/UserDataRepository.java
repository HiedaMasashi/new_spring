package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends
		JpaRepository<UserData, Long> {

	public List<UserData> findByName(String name);

	public List<UserData> findByNameAndId(String name, long id);

	public List<UserData> findByIdOrName(long id, String name);

	public List<UserData> findByIdBetween(long id1, long id2);

	public List<UserData> findByIdLessThan(long id);

	public List<UserData> findByMailIsNull();

	public List<UserData> findByIdIsNotNullOrderByIdDesc();

	public List<UserData> findByIdNot(long id);

}