package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import java.util.ListIterator;

public interface UniqueConstraint extends NestableAnnotation
{
	ListIterator<String> columnNames();

	void addColumnName(String columnName);
	
	void removeColumnName(String columnName);

	/**
	 * All containers must implement this interface.
	 */
	interface Owner
	{
		Iterator<String> candidateUniqueConstraintColumnNames();
		
		JavaResource javaResource();
	}
}
