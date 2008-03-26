/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaStructureNodes;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericJavaPersistentAttribute extends AbstractJavaJpaContextNode
	implements JavaPersistentAttribute
{
	protected String name;

	protected JavaAttributeMapping defaultMapping;

	protected JavaAttributeMapping specifiedMapping;

	protected JavaResourcePersistentAttribute resourcePersistentAttribute;

	public GenericJavaPersistentAttribute(JavaPersistentType parent) {
		super(parent);
	}
	
	public String getId() {
		return JavaStructureNodes.PERSISTENT_ATTRIBUTE_ID;
	}

	public void initializeFromResource(JavaResourcePersistentAttribute persistentAttributeResource) {
		this.resourcePersistentAttribute = persistentAttributeResource;
		this.name = this.name(persistentAttributeResource);
		initializeDefaultMapping(persistentAttributeResource);
		initializeSpecifiedMapping(persistentAttributeResource);
	}
	
	protected void initializeDefaultMapping(JavaResourcePersistentAttribute persistentAttributeResource) {
		this.defaultMapping = getJpaPlatform().buildDefaultJavaAttributeMapping(this);
		this.defaultMapping.initializeFromResource(persistentAttributeResource);
	}

	protected void initializeSpecifiedMapping(JavaResourcePersistentAttribute persistentAttributeResource) {
		String javaMappingAnnotationName = this.javaMappingAnnotationName(persistentAttributeResource);
		this.specifiedMapping = createJavaAttributeMappingFromAnnotation(javaMappingAnnotationName, persistentAttributeResource);
	}
	
	public JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return this.resourcePersistentAttribute;
	}
	
	public JavaPersistentType getPersistentType() {
		return (JavaPersistentType) this.getParent();
	}

	public JavaTypeMapping getTypeMapping() {
		return this.getPersistentType().getMapping();
	}

	public String getPrimaryKeyColumnName() {
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
	
	public boolean isVirtual() {
		return false;
	}
	
	public String getName() {
		return this.name;
	}
	
	protected void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public JavaAttributeMapping getDefaultMapping() {
		return this.defaultMapping;
	}

	/**
	 * clients do not set the "default" mapping
	 */
	protected void setDefaultMapping(JavaAttributeMapping newDefaultMapping) {
		JavaAttributeMapping oldMapping = this.defaultMapping;
		this.defaultMapping = newDefaultMapping;	
		firePropertyChanged(PersistentAttribute.DEFAULT_MAPPING_PROPERTY, oldMapping, newDefaultMapping);
	}

	public JavaAttributeMapping getSpecifiedMapping() {
		return this.specifiedMapping;
	}

	/**
	 * clients do not set the "specified" mapping;
	 * use #setMappingKey(String)
	 */
	protected void setSpecifiedMapping(JavaAttributeMapping newSpecifiedMapping) {
		JavaAttributeMapping oldMapping = this.specifiedMapping;
		this.specifiedMapping = newSpecifiedMapping;	
		firePropertyChanged(PersistentAttribute.SPECIFIED_MAPPING_PROPERTY, oldMapping, newSpecifiedMapping);
	}

	
	public JavaAttributeMapping getMapping() {
		return (this.specifiedMapping != null) ? this.specifiedMapping : this.defaultMapping;
	}

	public String getMappingKey() {
		return this.getMapping().getKey();
	}

	/**
	 * return null if there is no "default" mapping for the attribute
	 */
	public String getDefaultMappingKey() {
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
		JavaAttributeMapping oldMapping = getMapping();
		JavaAttributeMapping newMapping = createJavaAttributeMappingFromMappingKey(newKey);

		this.specifiedMapping = newMapping;	
		if (newMapping != null) {
			this.resourcePersistentAttribute.setMappingAnnotation(newMapping.annotationName());
		}
		else {
			this.resourcePersistentAttribute.setMappingAnnotation(null);			
		}
		firePropertyChanged(PersistentAttribute.SPECIFIED_MAPPING_PROPERTY, oldMapping, newMapping);
		
		if (oldMapping != null) {
			Collection<String> annotationsToRemove = CollectionTools.collection(oldMapping.correspondingAnnotationNames());
			if (getMapping() != null) {
				CollectionTools.removeAll(annotationsToRemove, getMapping().correspondingAnnotationNames());
			}
			
			for (String annotationName : annotationsToRemove) {
				this.resourcePersistentAttribute.removeAnnotation(annotationName);
			}
		}
	}
	
	public JpaStructureNode structureNode(int textOffset) {
		return this;
	}

	public boolean contains(int offset, CompilationUnit astRoot) {
		TextRange fullTextRange = this.fullTextRange(astRoot);
		if (fullTextRange == null) {
			//This happens if the attribute no longer exists in the java.
			//The text selection event is fired before the update from java so our
			//model has not yet had a chance to update appropriately. The list of
			//JavaPersistentAttriubtes is stale at this point.  For now, we are trying
			//to avoid the NPE, not sure of the ultimate solution to these 2 threads accessing
			//our model
			return false;
		}
		return fullTextRange.includes(offset);
	}


	public TextRange fullTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentAttribute.textRange(astRoot);
	}

	public TextRange validationTextRange(CompilationUnit astRoot) {
		return this.selectionTextRange(astRoot);
	}

	public TextRange selectionTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentAttribute.nameTextRange(astRoot);
	}
	
	public TextRange selectionTextRange() {
		return selectionTextRange(this.resourcePersistentAttribute.getMember().astRoot());
	}
	

	public void update(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		this.resourcePersistentAttribute = resourcePersistentAttribute;
		this.setName(this.name(resourcePersistentAttribute));
		this.updateDefaultMapping(resourcePersistentAttribute);
		this.updateSpecifiedMapping(resourcePersistentAttribute);
	}
	
	protected String name(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		return resourcePersistentAttribute.getName();	
	}
	
	public String specifiedMappingAnnotationName() {
		return (this.specifiedMapping == null) ? null : this.specifiedMapping.annotationName();
	}
	
	protected void updateSpecifiedMapping(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		String javaMappingAnnotationName = this.javaMappingAnnotationName(resourcePersistentAttribute);
		if (specifiedMappingAnnotationName() != javaMappingAnnotationName) {
			setSpecifiedMapping(createJavaAttributeMappingFromAnnotation(javaMappingAnnotationName, resourcePersistentAttribute));
		}
		else {
			if (getSpecifiedMapping() != null) {
				getSpecifiedMapping().update(resourcePersistentAttribute);
			}
		}
	}
	
	protected void updateDefaultMapping(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		String defaultMappingKey = getJpaPlatform().defaultJavaAttributeMappingKey(this);
		if (getDefaultMapping().getKey() != defaultMappingKey) {
			JavaAttributeMapping oldDefaultMapping = this.defaultMapping;
			this.defaultMapping = getJpaPlatform().buildDefaultJavaAttributeMapping(this);
			this.defaultMapping.initializeFromResource(resourcePersistentAttribute);
			firePropertyChanged(PersistentAttribute.DEFAULT_MAPPING_PROPERTY, oldDefaultMapping, this.defaultMapping);
		}
		else {
			getDefaultMapping().update(resourcePersistentAttribute);
		}
	}
	
	protected String javaMappingAnnotationName(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		Annotation mappingAnnotation = (Annotation) resourcePersistentAttribute.mappingAnnotation();
		if (mappingAnnotation != null) {
			return mappingAnnotation.getAnnotationName();
		}
		return null;
	}
	
	protected JavaAttributeMapping createJavaAttributeMappingFromMappingKey(String key) {
		if (key == MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY) {
			return null;
		}
		return getJpaPlatform().buildJavaAttributeMappingFromMappingKey(key, this);
	}

	protected JavaAttributeMapping createJavaAttributeMappingFromAnnotation(String annotationName, JavaResourcePersistentAttribute resourcePersistentAttribute) {
		if (annotationName == null) {
			return null;
		}
		JavaAttributeMapping mapping = getJpaPlatform().buildJavaAttributeMappingFromAnnotation(annotationName, this);
		mapping.initializeFromResource(resourcePersistentAttribute);
		return mapping;
	}

	/**
	 * the mapping might be "default", but it still might be a "null" mapping...
	 */
	public boolean mappingIsDefault(JavaAttributeMapping mapping) {
		return this.defaultMapping == mapping;
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return this.getMapping().javaCompletionProposals(pos, filter, astRoot);
	}
	
	//************* Validation ******************************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		if (this.specifiedMapping != null) {
			this.specifiedMapping.addToMessages(messages, astRoot);
		}
		else if (this.defaultMapping != null) {
			this.defaultMapping.addToMessages(messages, astRoot);
		}
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}
}
