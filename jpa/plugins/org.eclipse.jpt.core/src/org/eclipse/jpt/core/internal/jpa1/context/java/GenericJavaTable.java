/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaTable;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.TableAnnotation;

public class GenericJavaTable
	extends AbstractJavaTable
	implements JavaTable
{
	protected JavaResourcePersistentMember resourcePersistentMember;

	public GenericJavaTable(JavaEntity parent, Owner owner) {
		super(parent, owner);
	}
	
	public void initialize(JavaResourcePersistentMember pr) {
		this.resourcePersistentMember = pr;
		initialize(getAnnotation());
	}

	//query for the table resource every time on setters.
	//call one setter and the tableResource could change. 
	//You could call more than one setter before this object has received any notification
	//from the java resource model
	@Override
	protected TableAnnotation getAnnotation() {
		//TODO get the NullTable from the resource model or build it here in the context model??
		return (TableAnnotation) this.resourcePersistentMember.
				getNonNullAnnotation(getAnnotationName());
	}

	public boolean isResourceSpecified() {
		return getAnnotation().isSpecified();
	}
	
	@Override
	protected String getAnnotationName() {
		return TableAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	public JavaEntity getParent() {
		return (JavaEntity) super.getParent();
	}
	
	protected JavaEntity getJavaEntity() {
		return getParent();
	}
	
	@Override
	protected String buildDefaultName() {
		return this.getJavaEntity().getDefaultTableName();
	}

	/**
	 * Just to remember:
	 *   Entity.getDefaultSchema()
	 *     check inheritance - get default schema from root
	 *   EntityMappings.getSchema()
	 *     check for specified schema
	 *   PersistenceUnit.getDefaultSchema()
	 *     OrmPersistenceUnitDefaults.getSchema()
	 *   JpaProject.getDefaultSchema()
	 *     check for user override project setting
	 *   Catalog.getDefaultSchema()
	 *     or
	 *   Database.getDefaultSchema()
	 */
	@Override
	protected String buildDefaultSchema() {
		return this.getJavaEntity().getDefaultSchema();
	}
	
	@Override
	protected String buildDefaultCatalog() {
		return this.getJavaEntity().getDefaultCatalog();
	}

	public void update(JavaResourcePersistentMember jrpm) {
		this.resourcePersistentMember = jrpm;
		this.update(getAnnotation());
	}


	//******************* validation **********************

	public boolean shouldValidateAgainstDatabase() {
		return this.connectionProfileIsActive();
	}
}
