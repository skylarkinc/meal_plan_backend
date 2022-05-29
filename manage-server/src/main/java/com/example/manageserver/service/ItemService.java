package com.example.manageserver.service;

import com.example.manageserver.common.exception.ItemIdException;
import com.example.manageserver.common.exception.ItemNotFoundException;
import com.example.manageserver.model.Backlog;
import com.example.manageserver.model.Item;
import com.example.manageserver.model.User;
import com.example.manageserver.repository.BacklogRepository;
import com.example.manageserver.repository.ItemRepository;
import com.example.manageserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Item saveOrUpdateItem(Item item, String username) {

        if (item.getId() != null) {
            Item existingItem = itemRepository.findByItemIdentifier(item.getItemIdentifier());

            if (existingItem != null && (!existingItem.getItemOrderCreator().equals(username))) {
                throw new ItemNotFoundException("Project not found in your account");
            } else if (existingItem == null) {
                throw new ItemNotFoundException("Project with ID: '" + item.getItemIdentifier() + "' does not exist");
            }
        }

        try {
            User user = userRepository.findByUsername(username);
            item.setUser(user);
            item.setItemOrderCreator(user.getUsername());
            item.setItemIdentifier(item.getItemIdentifier().toUpperCase());

            if (item.getId() == null) {
                Backlog backlog = new Backlog();
                item.setBacklog(backlog);
                backlog.setItem(item);
                backlog.setItemIdentifier(item.getItemIdentifier().toUpperCase());
            }

            if (item.getId() != null) {
                item.setBacklog(backlogRepository.findByItemIdentifier(item.getItemIdentifier().toUpperCase()));
            }
            return itemRepository.save(item);
        } catch (Exception e) {
            throw new ItemIdException("Item ID '" + item.getItemIdentifier().toUpperCase() + "' already exists");
        }
    }

    public Item findItemByIdentifier(String itemId, String username) {

        Item item = itemRepository.findByItemIdentifier(itemId.toUpperCase());
        if (item == null) {
            throw new ItemIdException("Item ID '" + itemId + "' does not exists");
        }

        if (!item.getItemOrderCreator().equals(username)) {
            throw new ItemNotFoundException("Item not found in your account");
        }


        return item;
    }

    public Iterable<Item> findAllItems(String username) {
        return itemRepository.findAllByItemOrderCreator(username);
    }

    public void deleteItemByIdentifier(String itemId, String username) {

        itemRepository.delete(findItemByIdentifier(itemId, username));
    }
}
