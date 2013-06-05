/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.OrderBy;
import org.eclipse.jpt.jpa.core.internal.context.AbstractJpaContextModel;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOrderBy;

public class GenericOrmOrderBy
		extends AbstractJpaContextModel<JpaContextModel>
		implements OrderBy {
	
	protected Context context;
	
	protected String key;
	
	
	public GenericOrmOrderBy(JpaContextModel parent, Context context) {
		super(parent);
		this.context = context;
		initKey();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		synchKey();
	}
	
	
	// ***** key *****
	
	public String getKey() {
		return this.key;
	}
	
	public void setKey(String newKey) {
		this.context.getXmlOrderBy(true).setValue(newKey);
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
		XmlOrderBy xmlOrderBy = this.context.getXmlOrderBy(false);
		return (xmlOrderBy == null) ? null : xmlOrderBy.getValue();
	}
	
	public boolean isByPrimaryKey() {
		return StringTools.isBlank(this.key);
	}
	
	
	// ***** content assist / validation *****
	
	public TextRange getValidationTextRange() {
		XmlOrderBy xmlOrderBy = this.context.getXmlOrderBy(false);
		return (xmlOrderBy == null) ? getParent().getValidationTextRange() : xmlOrderBy.getValidationTextRange();
	}
	
	
	public interface Context {
		
		XmlOrderBy getXmlOrderBy(boolean addIfAbsent);
	}
}
