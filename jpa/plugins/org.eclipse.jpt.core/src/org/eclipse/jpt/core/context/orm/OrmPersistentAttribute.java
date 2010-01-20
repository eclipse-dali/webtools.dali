/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;

/**
 * ORM persistent attribute
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmPersistentAttribute
	extends PersistentAttribute, XmlContextNode
{

	// ********** covariant overrides **********

	OrmAttributeMapping getMapping();
	
	OrmAttributeMapping getSpecifiedMapping();
	
	OrmTypeMapping getOwningTypeMapping();
	
	OrmPersistentType getOwningPersistentType();

	
	// ********** java persistent attribute **********

	JavaPersistentAttribute getJavaPersistentAttribute();
		String JAVA_PERSISTENT_ATTRIBUTE_PROPERTY = "javaPersistentAttribute"; //$NON-NLS-1$


	// ********** virtual <-> specified **********

	/**
	 * Make the persistent attribute virtual. The attribute will be removed
	 * from the list of specified persistent attributes on the {@link OrmPersistentType} 
	 * and removed from the orm.xml file.
	 * 
	 * If the persistent attribute is already virtual, an IllegalStateException is thrown
	 * @see PersistentAttribute#isVirtual()
	 */
	void makeVirtual();
	
	/**
	 * Take a virtual persistent attribute and specify it.
	 * The attribute will be added to the list of specified persistent attributes
	 * and added to the orm.xml file. The mappingKey will remain the same.
	 * 
	 * If the persistent attribute is already specified, an IllegalStateException is thrown
	 * @see PersistentAttribute#isVirtual()
	 */
	void makeSpecified();
	
	/**
	 * Take a virtual persistent attribute and specify it.
	 * The attribute will be added to the list of specified persistent attributes
	 * and added to the orm.xml file. The mappingKey will determine the type of mapping added
	 * instead of the mappingKey already on the persistent attribute
	 * 
	 * If the persistent attribute is already specified, an IllegalStateException is thrown
	 * @see PersistentAttribute#isVirtual()
	 */
	void makeSpecified(String mappingKey);
	

	// ********** miscellaneous **********

	boolean contains(int textOffset);

	void nameChanged(String oldName, String newName);
	
	// ********** updating **********
	
	/**
	 * Update the OrmPersistentAttribute context model object to match the 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update();
	
	
	/**
	 * interface allowing persistent attributes to be used in multiple places
	 * (e.g. virtual and specified orm persistent attributes)
	 */
	interface Owner
	{
		/**
	 	 * Return the java persistent attribute that corresponds (same name and access type)
		 * to the given ormPersistentAttribute or null if none exists.
	 	 */
		JavaPersistentAttribute findJavaPersistentAttribute(OrmPersistentAttribute ormPersistentAttribute);
		
		/**
	 	 * Update the java persistent attribute if necessary, if it is owned by this object,
		 * it needs to be updated.
	 	 */
		void updateJavaPersistentAttribute();
	}


}
