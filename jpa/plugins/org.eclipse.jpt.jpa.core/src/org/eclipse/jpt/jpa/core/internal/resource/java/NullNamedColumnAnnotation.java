/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java;

import org.eclipse.jpt.common.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.NamedColumnAnnotation;

/**
 * <code><ul>
 * <li>javax.persistence.Column
 * <li>javax.persistence.JoinColumn
 * <li>javax.persistence.DiscriminatorColumn
 * <li>javax.persistence.PrimaryKeyJoinColumn
 * <li>javax.persistence.MapKeyColumn
 * <li>javax.persistence.MapKeyJoinColumn
 * <li>javax.persistence.OrderColumn
 * </ul></code>
 */
public abstract class NullNamedColumnAnnotation<A extends NamedColumnAnnotation>
	extends NullAnnotation<A>
	implements NamedColumnAnnotation
{
	protected NullNamedColumnAnnotation(JavaResourceModel parent) {
		super(parent);
	}

	public boolean isSpecified() {
		return false;
	}
	
	// ***** name
	public String getName() {
		return null;
	}

	public void setName(String name) {
		if (name != null) {
			this.addAnnotation().setName(name);
		}	
	}

	public TextRange getNameTextRange() {
		return null;
	}

	public boolean nameTouches(int pos) {
		return false;
	}

	public TextRange getNameValidationTextRange() {
		return null;
	}

	public boolean nameValidationTouches(int pos) {
		return false;
	}

	// ***** column definition
	public String getColumnDefinition() {
		return null;
	}

	public void setColumnDefinition(String columnDefinition) {
		if (columnDefinition != null) {
			this.addAnnotation().setColumnDefinition(columnDefinition);
		}	
	}

	public TextRange getColumnDefinitionTextRange() {
		return null;
	}
}
