/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;

/**
 * <code>orm.xml</code> type mapping
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.0
 */
public interface OrmTypeMapping
	extends TypeMapping, TypeRefactoringParticipant
{

	// ********** metadata complete **********

	boolean isMetadataComplete();
	Boolean getSpecifiedMetadataComplete();
	void setSpecifiedMetadataComplete(Boolean metadataComplete);
		String SPECIFIED_METADATA_COMPLETE_PROPERTY = "specifiedMetadataComplete"; //$NON-NLS-1$

	/**
	 * Override metadata complete is true if the type mapping's persistence
	 * unit is marked "XML mapping metadata complete".
	 */
	boolean isOverrideMetadataComplete();
		String OVERRIDE_METADATA_COMPLETE_PROPERTY = "overrideMetadataComplete"; //$NON-NLS-1$


	// ********** parent class **********

	/**
	 * <strong>NB:</strong> This may be an unqualified name to be prefixed by the
	 * entity mappings's package value.
	 * 
	 * @see EntityMappings#getPackage()
	 */
	String getParentClass();

	String getFullyQualifiedParentClass();
		String FULLY_QUALIFIED_PARENT_CLASS_PROPERTY = "fullyQualifiedParentClass"; //$NON-NLS-1$

	String getSpecifiedParentClass();

	/**
	 * @see #getParentClass()
	 */
	void setSpecifiedParentClass(String parentClass);
		String SPECIFIED_PARENT_CLASS_PROPERTY = "specifiedParentClass"; //$NON-NLS-1$

	String getDefaultParentClass();
		String DEFAULT_PARENT_CLASS_PROPERTY = "defaultParentClass"; //$NON-NLS-1$


	// ********** XML **********

	/**
	 * Type mappings are a sequence in the orm schema. We must keep
	 * the list of type mappings in the appropriate order so the wtp xml 
	 * translators will write them to the xml in that order and they
	 * will adhere to the schema.
	 * <p>
	 * Each concrete implementation must implement this
	 * method and return an int that matches its order in the schema.
	 * @see OrmManagedType#getXmlSequence()
	 */
	int getXmlSequence();
	
	/**
	 * Add the type mapping's XML type mapping to the appropriate list
	 * in the specified XML entity mappings.
	 */
	void addXmlTypeMappingTo(XmlEntityMappings entityMappings);

	/**
	 * Remove the type mapping's XML type mapping from the appropriate list
	 * in the specified XML entity mappings.
	 */
	void removeXmlTypeMappingFrom(XmlEntityMappings entityMappings);

	XmlTypeMapping getXmlTypeMapping();

	TextRange getSelectionTextRange();

	TextRange getAttributesTextRange();


	// ********** Java type mapping **********

	/**
	 * Return the Java type mapping corresponding to the <code>orm.xml</code>
	 * type mapping. Return <code>null</code> if there is no such Java type
	 * mapping; i.e. it does not exist or it is not the same type of type
	 * mapping (entity, mapped superclass, embeddable).
	 * 
	 * @see #getJavaTypeMappingForDefaults()
	 */
	JavaTypeMapping getJavaTypeMapping();
	
	/**
	 * Check "metadata complete" before returning the Java type mapping.
	 * For <code>orm.xml</code> defaults, if "metadata complete" is
	 * <code>true</code>, return <code>null</code>.
	 * 
	 * @see #getJavaTypeMapping()
	 */
	JavaTypeMapping getJavaTypeMappingForDefaults();
	

	// ********** misc **********

	OrmPersistentType getPersistentType();

	void initializeFrom(OrmTypeMapping oldMapping);
}
