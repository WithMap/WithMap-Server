package com.seoulapp.withmap.dao;

import java.util.List;

import com.seoulapp.withmap.model.Pin;

public interface PinDao {

	List<Pin> getAll();
	Pin get(final int id);
	void insert(final Pin pin);
	void update(final Pin pin);
	void delete(final int id);
}