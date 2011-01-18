/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * <code>orm.xml</code> type mapping
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
 */
public interface OrmTypeMapping
	extends TypeMapping, XmlContextNode
{
	// ********** class **********

	/**
	 * <strong>NB:</strong> This may be a partial name to be prefixed by the
	 * entity mappings's package value.
	 * 
	 * @see EntityMappings#getPackage()
	 */
	String getClass_();

	/**
	 * @see #getClass_()
	 */
	void setClass(String class_);
		String CLASS_PROPERTY = "class"; //$NON-NLS-1$


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


	// ********** XML **********

	/**
	 * Type mappings are a sequence in the orm schema. We must keep
	 * the list of type mappings in the appropriate order so the wtp xml 
	 * translators will write them to the xml in that order and they
	 * will adhere to the schema.
	 * <p>
	 * Each concrete implementation must implement this
	 * method and return an int that matches its order in the schema.
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

	TextRange getClassTextRange();

	TextRange getAttributesTextRange();
	
	boolean containsOffset(int textOffset);


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
	

	// ********** refactoring **********

	/**
	 * Create a text DeleteEdit for deleting the type mapping element and any text that precedes it
	 */
	DeleteEdit createDeleteEdit();

	/**
	 * Create ReplaceEdits for renaming any references to the originalType to the newName.
	 * The originalType has not yet been renamed, the newName is the new short name.
	 */
	Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName);

	/**
	 * Create ReplaceEdits for moving any references to the originalType to the newPackage.
	 * The originalType has not yet been moved.
	 */
	Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage);

	/**
	 * Create ReplaceEdits for renaming any references to the originalPackage to the newName.
	 * The originalPackage has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName);


	// ********** misc **********

	OrmPersistentType getPersistentType();

	void initializeFrom(OrmTypeMapping oldMapping);
}
