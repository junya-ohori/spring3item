package com.udemy.spring3item.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.udemy.spring3item.model.HelloMessage;
import com.udemy.spring3item.model.Item;
import com.udemy.spring3item.repo.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	private RestTemplate restTemplate;
	
	public ItemService (RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	@Cacheable("getItems")
	public List<Item> getAllItems() {
		List allItems = new ArrayList<>();
		try {
			Thread.sleep(3000); //キャッシュを使ったらここを通過しないので止まらない
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		itemRepository.findAll().forEach(allItems::add);
		return allItems; 
	}
	
	// Optionalにするのはnullが返却される可能性があるから
	@Cacheable(value="getItem", key="#p0")
	public Optional<Item> getItem(Long itemId) {
		try {
			Thread.sleep(3000); //キャッシュを使ったらここを通過しないので止まらない
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		return itemRepository.findById(itemId);
	}
	
	//データを追加したり削除したりすると、テーブルの状態が変わる＝キャッシュをクリアしないといけない
	@CacheEvict(value="getItems", allEntries=true)
	public void addItem(Item item) {
		itemRepository.save(item);
	}
	
	//getItemsとgetItemのどちらのキャッシュも消したい
	@Caching(evict = {
			@CacheEvict(value="getItem", key="#p0"),
			@CacheEvict(value="getItems", allEntries=true)
	})
	public void updateItem(Long itemId, Item item) {
		if (itemRepository.findById(itemId).get() != null) {
			itemRepository.save(item);
		}
	}
	
	@Caching(evict = {
			@CacheEvict(value="getItem", key="#p0"),
			@CacheEvict(value="getItems", allEntries=true)
	})
	public void deleteItem(Long itemId) {
		itemRepository.deleteById(itemId);
	}
	
	public HelloMessage getHelloResponse() {
		String URL = "http://localhost:8081/hello";
		String hello = restTemplate.getForObject(URL, String.class);
		HelloMessage retHello = new HelloMessage(hello);
		return retHello;
	}
}
