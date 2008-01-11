/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.ManyToOne;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaManyToOneMapping extends JavaSingleRelationshipMapping<ManyToOne>
	implements IJavaManyToOneMapping
{

	public JavaManyToOneMapping(IJavaPersistentAttribute parent) {
		super(parent);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.JOIN_COLUMN,
			JPA.JOIN_COLUMNS,
			JPA.JOIN_TABLE);
	}

	public String annotationName() {
		return ManyToOne.ANNOTATION_NAME;
	}

	public String getKey() {
		return IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected ManyToOne relationshipMapping() {
		return (ManyToOne) this.persistentAttributeResource.mappingAnnotation();
	}
	
	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
	
	@Override
	protected void setOptionalOnResourceModel(Boolean newOptional) {
		this.relationshipMapping().setOptional(newOptional);
	}
	
	@Override
	protected Boolean specifiedOptional(ManyToOne relationshipMapping) {
		return relationshipMapping.getOptional();
	}
	
	
	//***************** ISingleRelationshipMapping implementation *****************
	@Override
	@SuppressWarnings("unchecked")
	//overriding purely to suppress the warning you get at the class level
	public ListIterator<IJavaJoinColumn> joinColumns() {
		return super.joinColumns();
	}

	@Override
	@SuppressWarnings("unchecked")
	//overriding purely to suppress the warning you get at the class level
	public ListIterator<IJavaJoinColumn> defaultJoinColumns() {
		return super.defaultJoinColumns();
	}

	@Override
	@SuppressWarnings("unchecked")
	//overriding purely to suppress the warning you get at the class level
	public ListIterator<IJavaJoinColumn> specifiedJoinColumns() {
		return super.specifiedJoinColumns();
	}


}