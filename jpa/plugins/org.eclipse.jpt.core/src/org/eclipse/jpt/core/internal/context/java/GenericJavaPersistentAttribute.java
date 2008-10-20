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
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public class GenericJavaPersistentAttribute
	extends AbstractJavaJpaContextNode
	implements JavaPersistentAttribute
{
	protected String name;

	protected JavaAttributeMapping defaultMapping;

	protected JavaAttributeMapping specifiedMapping;

	protected JavaResourcePersistentAttribute resourcePersistentAttribute;


	public GenericJavaPersistentAttribute(JavaPersistentType parent, JavaResourcePersistentAttribute jrpa) {
		super(parent);
		this.initialize(jrpa);
	}
	
	public String getId() {
		return JavaStructureNodes.PERSISTENT_ATTRIBUTE_ID;
	}

	protected void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		this.name = this.name(jrpa);
		initializeDefaultMapping(jrpa);
		initializeSpecifiedMapping(jrpa);
	}
	
	protected void initializeDefaultMapping(JavaResourcePersistentAttribute jrpa) {
		this.defaultMapping = getJpaPlatform().buildDefaultJavaAttributeMapping(this);
		this.defaultMapping.initialize(jrpa);
	}

	protected void initializeSpecifiedMapping(JavaResourcePersistentAttribute jrpa) {
		String javaMappingAnnotationName = this.javaMappingAnnotationName(jrpa);
		this.specifiedMapping = createJavaAttributeMappingFromAnnotation(javaMappingAnnotationName, jrpa);
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
		return this.getMapping().getPrimaryKeyColumnName();
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
	public String getSpecifiedMappingKey() {
		return (this.specifiedMapping == null) ? null : this.specifiedMapping.getKey();
	}

	// TODO support morphing mappings, i.e. copying common settings over
	// to the new mapping; this can't be done in the same was as XmlAttributeMapping
	// since we don't know all the possible mapping types
	public void setSpecifiedMappingKey(String newKey) {
		if (newKey == getSpecifiedMappingKey()) {
			return;
		}
		JavaAttributeMapping oldMapping = getMapping();
		JavaAttributeMapping newMapping = createJavaAttributeMappingFromMappingKey(newKey);

		this.specifiedMapping = newMapping;	
		if (newMapping != null) {
			this.resourcePersistentAttribute.setMappingAnnotation(newMapping.getAnnotationName());
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
				this.resourcePersistentAttribute.removeSupportingAnnotation(annotationName);
			}
		}
	}
	
	public JpaStructureNode getStructureNode(int textOffset) {
		return this;
	}

	public boolean contains(int offset, CompilationUnit astRoot) {
		TextRange fullTextRange = this.getFullTextRange(astRoot);
		// 'fullTextRange' will be null if the attribute no longer exists in the java;
		// the context model can be out of synch with the resource model
		// when a selection event occurs before the context model has a
		// chance to synch with the resource model via the update thread
		return (fullTextRange == null) ? false : fullTextRange.includes(offset);
	}


	public TextRange getFullTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentAttribute.getTextRange(astRoot);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getSelectionTextRange(astRoot);
	}

	public TextRange getSelectionTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentAttribute.getNameTextRange(astRoot);
	}
	
	public TextRange getSelectionTextRange() {
		return getSelectionTextRange(this.buildASTRoot());
	}

	protected CompilationUnit buildASTRoot() {
		return JDTTools.buildASTRoot(this.resourcePersistentAttribute.getJpaCompilationUnit().getCompilationUnit());
	}

	public void update() {
		this.setName(this.name(this.resourcePersistentAttribute));
		this.updateDefaultMapping(this.resourcePersistentAttribute);
		this.updateSpecifiedMapping(this.resourcePersistentAttribute);
	}
	
	protected String name(JavaResourcePersistentAttribute jrpa) {
		return jrpa.getName();	
	}
	
	public String specifiedMappingAnnotationName() {
		return (this.specifiedMapping == null) ? null : this.specifiedMapping.getAnnotationName();
	}
	
	protected void updateSpecifiedMapping(JavaResourcePersistentAttribute jrpa) {
		String javaMappingAnnotationName = this.javaMappingAnnotationName(jrpa);
		if (specifiedMappingAnnotationName() != javaMappingAnnotationName) {
			setSpecifiedMapping(createJavaAttributeMappingFromAnnotation(javaMappingAnnotationName, jrpa));
		}
		else {
			if (getSpecifiedMapping() != null) {
				getSpecifiedMapping().update(jrpa);
			}
		}
	}
	
	protected void updateDefaultMapping(JavaResourcePersistentAttribute jrpa) {
		String defaultMappingKey = getJpaPlatform().getDefaultJavaAttributeMappingKey(this);
		if (getDefaultMapping().getKey() != defaultMappingKey) {
			JavaAttributeMapping oldDefaultMapping = this.defaultMapping;
			this.defaultMapping = getJpaPlatform().buildDefaultJavaAttributeMapping(this);
			this.defaultMapping.initialize(jrpa);
			firePropertyChanged(PersistentAttribute.DEFAULT_MAPPING_PROPERTY, oldDefaultMapping, this.defaultMapping);
		}
		else {
			getDefaultMapping().update(jrpa);
		}
	}
	
	protected String javaMappingAnnotationName(JavaResourcePersistentAttribute jrpa) {
		Annotation mappingAnnotation = (Annotation) jrpa.getMappingAnnotation();
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

	protected JavaAttributeMapping createJavaAttributeMappingFromAnnotation(String annotationName, JavaResourcePersistentAttribute jrpa) {
		if (annotationName == null) {
			return null;
		}
		JavaAttributeMapping mapping = getJpaPlatform().buildJavaAttributeMappingFromAnnotation(annotationName, this);
		mapping.initialize(jrpa);
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


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		
		if (this.specifiedMapping != null) {
			this.specifiedMapping.validate(messages, astRoot);
		}
		else if (this.defaultMapping != null) {
			this.defaultMapping.validate(messages, astRoot);
		}
	}
	

	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.name);
	}
	
	public void dispose() {
		//nothing to dispose
	}

}
