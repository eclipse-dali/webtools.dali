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

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.java.Annotation;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;

public class JavaPersistentAttribute extends JavaContextModel
	implements IJavaPersistentAttribute
{
	protected String name;

	protected IJavaAttributeMapping defaultMapping;

	protected IJavaAttributeMapping specifiedMapping;

	protected JavaPersistentAttributeResource persistentAttributeResource;

	public JavaPersistentAttribute(IJavaPersistentType parent) {
		super(parent);
	}
	
	public void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource) {
		this.persistentAttributeResource = persistentAttributeResource;
		this.name = this.name(persistentAttributeResource);
		initializeDefaultMapping(persistentAttributeResource);
		initializeSpecifiedMapping(persistentAttributeResource);
	}
	
	protected void initializeDefaultMapping(JavaPersistentAttributeResource persistentAttributeResource) {
		this.defaultMapping = createDefaultJavaAttributeMapping(persistentAttributeResource);
	}

	protected void initializeSpecifiedMapping(JavaPersistentAttributeResource persistentAttributeResource) {
		String javaMappingAnnotationName = this.javaMappingAnnotationName(persistentAttributeResource);
		this.specifiedMapping = createJavaAttributeMappingFromAnnotation(javaMappingAnnotationName, persistentAttributeResource);
	}
	
	public JavaPersistentAttributeResource getPersistentAttributeResource() {
		return this.persistentAttributeResource;
	}
	
	public IPersistentType getPersistentType() {
		return (IPersistentType) this.parent();
	}

	public ITypeMapping typeMapping() {
		return this.getPersistentType().getMapping();
	}

	public String primaryKeyColumnName() {
		return this.getMapping().primaryKeyColumnName();
	}

	public boolean isOverridableAttribute() {
		return this.getMapping().isOverridableAttributeMapping();
	}

	public boolean isOverridableAssociation() {
		return this.getMapping().isOverridableAssociationMapping();
	}

	public boolean isIdAttribute() {
		return this.getMapping().isIdMapping();
	}
	
	public String getName() {
		return this.name;
	}
	
	protected void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public IJavaAttributeMapping getDefaultMapping() {
		return this.defaultMapping;
	}

	/**
	 * clients do not set the "default" mapping
	 */
	protected void setDefaultMapping(IJavaAttributeMapping newDefaultMapping) {
		IJavaAttributeMapping oldMapping = this.defaultMapping;
		this.defaultMapping = newDefaultMapping;	
		firePropertyChanged(IPersistentAttribute.DEFAULT_MAPPING_PROPERTY, oldMapping, newDefaultMapping);
	}

	public IJavaAttributeMapping getSpecifiedMapping() {
		return this.specifiedMapping;
	}

	/**
	 * clients do not set the "specified" mapping;
	 * use #setMappingKey(String)
	 */
	protected void setSpecifiedMapping(IJavaAttributeMapping newSpecifiedMapping) {
		IJavaAttributeMapping oldMapping = this.specifiedMapping;
		this.specifiedMapping = newSpecifiedMapping;	
		firePropertyChanged(IPersistentAttribute.SPECIFIED_MAPPING_PROPERTY, oldMapping, newSpecifiedMapping);
	}

	
	public IJavaAttributeMapping getMapping() {
		return (this.specifiedMapping != null) ? this.specifiedMapping : this.defaultMapping;
	}

	public String mappingKey() {
		return this.getMapping().getKey();
	}

	/**
	 * return null if there is no "default" mapping for the attribute
	 */
	public String defaultMappingKey() {
		return this.defaultMapping.getKey();
	}

	/**
	 * return null if there is no "specified" mapping for the attribute
	 */
	public String specifiedMappingKey() {
		return (this.specifiedMapping == null) ? null : this.specifiedMapping.getKey();
	}

	// TODO support morphing mappings, i.e. copying common settings over
	// to the new mapping; this can't be done in the same was as XmlAttributeMapping
	// since we don't know all the possible mapping types
	public void setSpecifiedMappingKey(String newKey) {
		if (newKey == specifiedMappingKey()) {
			return;
		}
		IJavaAttributeMapping oldMapping = getMapping();
		IJavaAttributeMapping newMapping = createJavaAttributeMappingFromMappingKey(newKey);
		setSpecifiedMapping(newMapping);
		if (newMapping != null) {
			this.persistentAttributeResource.setMappingAnnotation(newMapping.annotationName());
		}
		else {
			this.persistentAttributeResource.setMappingAnnotation(null);			
		}
		
		if (oldMapping != null) {
			Collection<String> annotationsToRemove = CollectionTools.collection(oldMapping.correspondingAnnotationNames());
			if (getMapping() != null) {
				CollectionTools.removeAll(annotationsToRemove, getMapping().correspondingAnnotationNames());
			}
			
			for (String annotationName : annotationsToRemove) {
				this.persistentAttributeResource.removeAnnotation(annotationName);
			}
		}
	}

//	public boolean includes(int offset) {
//		ITextRange fullTextRange = this.fullTextRange();
//		if (fullTextRange == null) {
//			//This happens if the attribute no longer exists in the java.
//			//The text selection event is fired before the update from java so our
//			//model has not yet had a chance to update appropriately. The list of
//			//JavaPersistentAttriubtes is stale at this point.  For now, we are trying
//			//to avoid the NPE, not sure of the ultimate solution to these 2 threads accessing
//			//our model
//			return false;
//		}
//		return fullTextRange.includes(offset);
//	}
//
//	public ITextRange fullTextRange() {
//		return this.attribute.textRange();
//	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		return this.selectionTextRange(astRoot);
	}

	public ITextRange selectionTextRange(CompilationUnit astRoot) {
		return this.persistentAttributeResource.textRange(astRoot);
	}

	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		this.persistentAttributeResource = persistentAttributeResource;
		this.setName(this.name(persistentAttributeResource));
		this.updateDefaultMapping(persistentAttributeResource);
		this.updateSpecifiedMapping(persistentAttributeResource);
	}
	
	protected String name(JavaPersistentAttributeResource persistentAttributeResource) {
		return persistentAttributeResource.getName();	
	}
	
	public String specifiedMappingAnnotationName() {
		return (this.specifiedMapping == null) ? null : this.specifiedMapping.annotationName();
	}
	
	protected void updateSpecifiedMapping(JavaPersistentAttributeResource persistentAttributeResource) {
		String javaMappingAnnotationName = this.javaMappingAnnotationName(persistentAttributeResource);
		if (specifiedMappingAnnotationName() != javaMappingAnnotationName) {
			setSpecifiedMapping(createJavaAttributeMappingFromAnnotation(javaMappingAnnotationName, persistentAttributeResource));
		}
		else {
			if (getSpecifiedMapping() != null) {
				getSpecifiedMapping().update(persistentAttributeResource);
			}
		}
	}
	
	protected void updateDefaultMapping(JavaPersistentAttributeResource persistentAttributeResource) {
		String defaultMappingKey = jpaPlatform().defaultJavaAttributeMappingKey(this);
		if (getDefaultMapping().getKey() != defaultMappingKey) {
			setDefaultMapping(createDefaultJavaAttributeMapping(persistentAttributeResource));
		}
		else {
			getDefaultMapping().update(persistentAttributeResource);
		}
	}
	
	protected String javaMappingAnnotationName(JavaPersistentAttributeResource persistentAttributeResource) {
		Annotation mappingAnnotation = (Annotation) persistentAttributeResource.mappingAnnotation();
		if (mappingAnnotation != null) {
			return mappingAnnotation.getAnnotationName();
		}
		return null;
	}
	
	protected IJavaAttributeMapping createJavaAttributeMappingFromMappingKey(String key) {
		if (key == IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY) {
			return null;
		}
		return jpaPlatform().createJavaAttributeMappingFromMappingKey(key, this);
	}

	protected IJavaAttributeMapping createJavaAttributeMappingFromAnnotation(String annotationName, JavaPersistentAttributeResource persistentAttributeResource) {
		if (annotationName == null) {
			return null;
		}
		IJavaAttributeMapping mapping = jpaPlatform().createJavaAttributeMappingFromAnnotation(annotationName, this);
		mapping.initializeFromResource(persistentAttributeResource);
		return mapping;
	}

	protected IJavaAttributeMapping createDefaultJavaAttributeMapping(JavaPersistentAttributeResource persistentAttributeResource) {		
		IJavaAttributeMapping defaultMapping = jpaPlatform().createDefaultJavaAttributeMapping(this);
		defaultMapping.initializeFromResource(persistentAttributeResource);
		return defaultMapping;
	}

	/**
	 * the mapping might be "default", but it still might be a "null" mapping...
	 */
	public boolean mappingIsDefault() {
		return this.specifiedMapping == null;
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return this.getMapping().candidateValuesFor(pos, filter, astRoot);
	}

}
