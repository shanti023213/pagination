package com.pagination;

public class Book {
    private int id;
    private String name;
    private int number;

    public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Book(int id, String name,int number) {
        super();
        this.id = id;
        this.name = name;
        this.number=number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}