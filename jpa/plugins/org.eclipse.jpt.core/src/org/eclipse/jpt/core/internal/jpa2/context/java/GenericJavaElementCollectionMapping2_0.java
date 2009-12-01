/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.Vector;

import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Fetchable;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;


public class GenericJavaElementCollectionMapping2_0
	extends AbstractJavaAttributeMapping<ElementCollection2_0Annotation>
	implements JavaElementCollectionMapping2_0
{
		protected FetchType specifiedFetch;

	public GenericJavaElementCollectionMapping2_0(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.specifiedFetch = this.getResourceFetch();
	}
	
	@Override
	protected void update() {
		super.update();
		this.setSpecifiedFetch_(this.getResourceFetch());
	}

	//************** JavaAttributeMapping implementation ***************

	public String getKey() {
		return MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return ElementCollection2_0Annotation.ANNOTATION_NAME;
	}
	
	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(JPA.ASSOCIATION_OVERRIDE);
		names.add(JPA.ASSOCIATION_OVERRIDES);
		names.add(JPA.ATTRIBUTE_OVERRIDE);
		names.add(JPA.ATTRIBUTE_OVERRIDES);
//		names.add(JPA2_0.COLLECTION_TABLE);
		names.add(JPA.COLUMN);
		names.add(JPA.ENUMERATED);
		names.add(JPA.LOB);
		names.add(JPA.MAP_KEY);
//		names.add(JPA2_0.MAP_KEY_CLASS);
//		names.add(JPA2_0.MAP_KEY_COLUMN);
//		names.add(JPA2_0.MAP_KEY_ENUMERATED);
//		names.add(JPA2_0.MAP_KEY_JOIN_COLUMN);
//		names.add(JPA2_0.MAP_KEY_JOIN_COLUMNS);
//		names.add(JPA2_0.MAP_KEY_TEMPORAL);
		names.add(JPA.ORDER_BY);
//		names.add(JPA2_0.ORDER_COLUMN);
		names.add(JPA.TEMPORAL);
	}
	
	
	// *************** Fetch ***************
	
	public FetchType getFetch() {
		return (this.getSpecifiedFetch() == null) ? this.getDefaultFetch() : this.getSpecifiedFetch();
	}

	public FetchType getDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}
		
	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}
	
	public void setSpecifiedFetch(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		this.mappingAnnotation.setFetch(FetchType.toJavaResourceModel(newSpecifiedFetch));
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedFetch_(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}
	
	protected FetchType getResourceFetch() {
		return FetchType.fromJavaResourceModel(this.mappingAnnotation.getFetch());
	}

}
