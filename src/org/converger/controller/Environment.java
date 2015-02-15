package org.converger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.converger.framework.Expression;

/**
 * Represent a a list of {@link Record}.
 * Its list represents the current environment of the application: all the mathematical 
 * expression used, the file where them are saved and all the functions for modify the environment, 
 * such edit or delete an expression.
 * @author Gabriele Graffieti
 *
 */
public class Environment {
	
	private final List<Record> recordList;
	private Optional<String> filePath;
	private boolean mod;
	
	/**
	 * Constructs a new empty collection of expression, with no file linked.
	 */
	public Environment() {
		this.recordList = new ArrayList<>();
		this.filePath = Optional.empty();
		this.mod = false;
	}
	
	/** @return a boolean which indicates if the file is modified or not. */
	public boolean isModified() {
		return this.mod;
	}
	
	/** @return the record list. */
	public List<Record> getRecordList() {
		return new ArrayList<>(this.recordList);
	}
	
	/** @return an optional string value which indicates the path of the file. If the file is not saved return an Optional.enpty  */
	public Optional<String> getFilePath() {
		return this.filePath;
	}
	
	/** 
	 * Set a new path for the file. 
	 * @param path the new path of the file.  
	 */
	public void setFilePath(final String path) {
		this.filePath = Optional.ofNullable(path);
	}

	/**
	 * Add a new record to the list.
	 * @param exp a new record which will be added to the list.
	 */
	public void add(final Record exp) {
		this.recordList.add(exp);
		this.modified();
	}
	
	/**
	 * Delete a record from the list. It takes in input an integer value representing the index in the list of the expression.
	 * @param index the index of the expression which will be removed.
	 */
	public void delete(final int index) {
		this.recordList.remove(index);
		this.modified();
	}
	
	/**
	 * delete all the expression in the current environment.
	 */
	public void deleteAll() {
		this.recordList.clear();
		this.modified();
	}
	
	/**
	 * Modifies a record selected by its index. The function required a new expression plain text, a new expression latex text 
	 * and the new expression Expression.
	 * @param index the index of the expression to be modified
	 * @param plainText the plain text of the new expression.
	 * @param latexText the latex text of the new expression.
	 * @param exp the new expression
	 */
	public void modifyExpression(final int index, final String plainText, final String latexText, final Expression exp) {
		this.recordList.get(index).modify(plainText, latexText, exp);
		this.modified();
	}
	
	private void modified() {
		this.mod = true;
	}
	
	

}
