package com.example.onlinephoneshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.onlinephoneshop.entity.ViewHistory;
import com.example.onlinephoneshop.entity.ViewHistory.ViewHistoryId;

@Repository
public interface ViewHistoryRepository extends JpaRepository<ViewHistory, ViewHistoryId> {
	List<ViewHistory> findByViewHistoryIdUserId(String userId);
	List<ViewHistory> findAllByOrderByUpdatedDateDesc();
	Boolean existsByViewHistoryId(ViewHistoryId viewHistoryId);
}
