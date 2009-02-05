/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.Iterator;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmTypeMapping
	extends TypeMapping, XmlContextNode
{
	
	String getClass_();
	void setClass(String newClass);
	String CLASS_PROPERTY = "class"; //$NON-NLS-1$

	
	boolean isMetadataComplete();
	Boolean getSpecifiedMetadataComplete();
	void setSpecifiedMetadataComplete(Boolean newSpecifiedMetadataComplete);
		String SPECIFIED_METADATA_COMPLETE_PROPERTY = "specifiedMetadataComplete"; //$NON-NLS-1$

	boolean isDefaultMetadataComplete();
		String DEFAULT_METADATA_COMPLETE_PROPERTY = "defaultMetadataComplete"; //$NON-NLS-1$

	
	/**
	 * type mappings are a sequence in the orm schema. We must keep
	 * the list of type mappings in the appropriate order so the wtp xml 
	 * translators will write them to the xml in that order and they
	 * will adhere to the schema.  
	 * 
	 * Each concrete subclass of XmlTypeMapping must implement this
	 * method and return an int that matches it's order in the schema
	 * @return
	 */
	int getXmlSequence();

	void removeFromResourceModel(XmlEntityMappings entityMappings);
		
	XmlTypeMapping addToResourceModel(XmlEntityMappings entityMappings);

	void initializeFrom(OrmTypeMapping oldMapping);

	XmlTypeMapping getResourceTypeMapping();

	IContentType getContentType();

	TextRange getSelectionTextRange();

	TextRange getClassTextRange();

	TextRange getAttributesTextRange();
	
	boolean containsOffset(int textOffset);
	

	void initialize(XmlTypeMapping typeMapping);

	/**
	 * Update the OrmTypeMapping context model object to match the 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update();

	// ********** covariant overrides **********

	OrmPersistentType getPersistentType();
	
	@SuppressWarnings("unchecked")
	Iterator<OrmPersistentAttribute> overridableAttributes();
	
	@SuppressWarnings("unchecked")
	Iterator<OrmPersistentAttribute> overridableAssociations();

}
