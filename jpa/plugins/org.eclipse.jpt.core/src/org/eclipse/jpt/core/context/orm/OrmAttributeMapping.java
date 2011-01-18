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
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * <code>orm.xml</code> attribute mapping
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.0
 * @since 2.3
 */
public interface OrmAttributeMapping
	extends AttributeMapping, XmlContextNode
{
	OrmPersistentAttribute getParent();

	OrmPersistentAttribute getPersistentAttribute();

	XmlAttributeMapping getXmlAttributeMapping();

	OrmTypeMapping getTypeMapping();

	void setName(String name);
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Attributes are a sequence in the <code>orm.xml</code> schema. We must keep
	 * the list of attributes in the appropriate order so the WTP XML
	 * translators will write them to the XML document in that order and they
	 * will adhere to the schema.
	 * <p>
	 * Each implementation must implement this
	 * method and return a number that matches its order in the schema.
	 */
	int getXmlSequence();

	void addXmlAttributeMappingTo(Attributes resourceAttributes);

	void removeXmlAttributeMappingFrom(Attributes resourceAttributes);

	boolean contains(int textOffset);

	TextRange getSelectionTextRange();

	TextRange getNameTextRange();


	// ********** morphing mappings **********

	void initializeOn(OrmAttributeMapping newMapping);

	void initializeFromOrmAttributeMapping(OrmAttributeMapping oldMapping);

	void initializeFromOrmBasicMapping(OrmBasicMapping oldMapping);

	void initializeFromOrmIdMapping(OrmIdMapping oldMapping);

	void initializeFromOrmTransientMapping(OrmTransientMapping oldMapping);

	void initializeFromOrmEmbeddedMapping(OrmEmbeddedMapping oldMapping);

	void initializeFromOrmEmbeddedIdMapping(OrmEmbeddedIdMapping oldMapping);

	void initializeFromOrmVersionMapping(OrmVersionMapping oldMapping);

	void initializeFromOrmOneToManyMapping(OrmOneToManyMapping oldMapping);

	void initializeFromOrmManyToOneMapping(OrmManyToOneMapping oldMapping);

	void initializeFromOrmOneToOneMapping(OrmOneToOneMapping oldMapping);

	void initializeFromOrmManyToManyMapping(OrmManyToManyMapping oldMapping);


	// ********** refactoring **********

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
}
