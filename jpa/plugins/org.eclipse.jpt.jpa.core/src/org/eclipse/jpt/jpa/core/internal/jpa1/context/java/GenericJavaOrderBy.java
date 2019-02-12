/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.OrderBy;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.resource.java.OrderByAnnotation;

public class GenericJavaOrderBy
		extends AbstractJavaContextModel<JpaContextModel>
		implements OrderBy {
	
	protected Context context;
	
	protected String key;
	
	
	public GenericJavaOrderBy(JpaContextModel parent, Context context) {
		super(parent);
		this.context = context;
		initKey();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		synchKey();
	}
	
	
	// ***** key *****
	
	public String getKey() {
		return this.key;
	}
	
	public void setKey(String newKey) {
		if (newKey == null) {
			OrderByAnnotation annotation = this.context.getAnnotation(false);
			if (annotation != null) {
				annotation.setValue(null);
			}
		}
		else {
			this.context.getAnnotation(true).setValue(newKey);
		}
		setKey_(newKey);
	}
	
	protected void setKey_(String newKey) {
		String oldKey = this.key;
		this.key = newKey;
		firePropertyChanged(KEY_PROPERTY, oldKey, newKey);
	}
	
	protected void initKey() {
		this.key = getResourceKey();
	}
	
	protected void synchKey() {
		setKey_(getResourceKey());
	}
	
	protected String getResourceKey() {
		OrderByAnnotation annotation = this.context.getAnnotation(false);
		return (annotation == null) ? null : annotation.getValue();
	}
	
	public boolean isByPrimaryKey() {
		return StringTools.isBlank(this.key);
	}
	
	
	// ***** content assist / validation *****
	
	public TextRange getValidationTextRange() {
		OrderByAnnotation annotation = this.context.getAnnotation(false);
		return (annotation == null) ? getParent().getValidationTextRange() : annotation.getTextRange();
	}
	
	
	public interface Context {
		
		OrderByAnnotation getAnnotation(boolean addIfAbsent);
	}
}
