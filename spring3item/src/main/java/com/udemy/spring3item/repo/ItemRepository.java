package com.udemy.spring3item.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.udemy.spring3item.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
	//インターフェースは用意しなくていい。アクセサは裏で用意してくれている。
	//CrudRepositoryクラスがfindByIdとか定番メソッドは用意してくれている
}
