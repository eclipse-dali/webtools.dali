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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaStructureNodes;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericJavaPersistentType extends AbstractJavaJpaContextNode implements JavaPersistentType
{
	protected String name;
	
	protected JavaTypeMapping mapping;

	protected final List<JavaPersistentAttribute> attributes;

	protected AccessType access;

	protected PersistentType parentPersistentType;

	protected JavaResourcePersistentType resourcePersistentType;

	public GenericJavaPersistentType(JpaContextNode parent, JavaResourcePersistentType jrpt) {
		super(parent);
		this.attributes = new ArrayList<JavaPersistentAttribute>();
		this.initialize(jrpt);
	}
	
	@Override
	public IResource getResource() {
		return this.resourcePersistentType.getResourceModel().getJpaCompilationUnit().getCompilationUnit().getResource();
	}

	//****************** JpaStructureNode implementation *******************
	
	public String getId() {
		return JavaStructureNodes.PERSISTENT_TYPE_ID;
	}
	
	//****************** PersistentType implementation *******************
	public String getName() {
		return this.name;
	}
	
	protected void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	public JavaTypeMapping getMapping() {
		return this.mapping;
	}

	public String getMappingKey() {
		return getMapping().getKey();
	}
	
	public void setMappingKey(String key) {
		if (key == getMapping().getKey()) {
			return;
		}
		JavaTypeMapping oldMapping = getMapping();
		JavaTypeMapping newMapping = createJavaTypeMappingFromMappingKey(key);
	
		this.mapping = newMapping;	
		this.resourcePersistentType.setMappingAnnotation(newMapping.getAnnotationName());
		firePropertyChanged(PersistentType.MAPPING_PROPERTY, oldMapping, newMapping);
	
		if (oldMapping != null) {
			Collection<String> annotationsToRemove = CollectionTools.collection(oldMapping.correspondingAnnotationNames());
			if (getMapping() != null) {
				CollectionTools.removeAll(annotationsToRemove, getMapping().correspondingAnnotationNames());
			}
			
			for (String annotationName : annotationsToRemove) {
				this.resourcePersistentType.removeAnnotation(annotationName);
			}
		}
	}
	
	protected void setMapping(JavaTypeMapping newMapping) {
		JavaTypeMapping oldMapping = this.mapping;
		this.mapping = newMapping;	
		firePropertyChanged(PersistentType.MAPPING_PROPERTY, oldMapping, newMapping);
	}
	
	public boolean isMapped() {
		return getMapping().isMapped();
	}

	public AccessType getAccess() {
		return this.access;
	}
	
	protected void setAccess(AccessType newAccess) {
		AccessType oldAccess = this.access;
		this.access = newAccess;
		firePropertyChanged(PersistentType.ACCESS_PROPERTY, oldAccess, newAccess);
	}

	protected Iterator<JavaPersistentAttribute> attributesNamed(final String attributeName) {
		return new FilteringIterator<JavaPersistentAttribute, JavaPersistentAttribute>(attributes()) {
			@Override
			protected boolean accept(JavaPersistentAttribute o) {
				return attributeName.equals(o.getName());
			}
		};
	}

	public JavaPersistentAttribute getAttributeNamed(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = attributesNamed(attributeName);
		return (stream.hasNext()) ? stream.next() : null;
	}

	public PersistentAttribute resolveAttribute(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = attributesNamed(attributeName);
		if (stream.hasNext()) {
			JavaPersistentAttribute attribute = stream.next();
			return (stream.hasNext()) ? null /*more than one*/: attribute;
		}
		return (getParentPersistentType() == null) ? null : getParentPersistentType().resolveAttribute(attributeName);
	}
	
	public ListIterator<JavaPersistentAttribute> attributes() {
		return new CloneListIterator<JavaPersistentAttribute>(this.attributes);
	}
	
	public int attributesSize() {
		return this.attributes.size();
	}
	
	private void addAttribute(int index, JavaPersistentAttribute attribute) {
		addItemToList(index, attribute, this.attributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}

	private void removeAttribute(JavaPersistentAttribute attribute) {
		removeItemFromList(attribute, this.attributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}
	
	private void moveAttribute(int index, JavaPersistentAttribute attribute) {
		moveItemInList(index, this.attributes.indexOf(attribute), this.attributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}
	
	public Iterator<String> attributeNames() {
		return this.attributeNames(this.attributes());
	}
	
	protected Iterator<String> attributeNames(Iterator<? extends PersistentAttribute> attrs) {
		return new TransformationIterator<PersistentAttribute, String>(attrs) {
			@Override
			protected String transform(PersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}
	
	public Iterator<PersistentAttribute> allAttributes() {
		return new CompositeIterator<PersistentAttribute>(new TransformationIterator<PersistentType, Iterator<PersistentAttribute>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<PersistentAttribute> transform(PersistentType pt) {
				return pt.attributes();
			}
		});
	}
	
	public Iterator<String> allAttributeNames() {
		return this.attributeNames(this.allAttributes());
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		Iterator<String> values = this.mapping.javaCompletionProposals(pos, filter, astRoot);
		if (values != null) {
			return values;
		}
		for (Iterator<JavaPersistentAttribute> stream = attributes(); stream.hasNext();) {
			values = stream.next().javaCompletionProposals(pos, filter, astRoot);
			if (values != null) {
				return values;
			}
		}
		return EmptyIterator.instance();
	}
	
	// it would be nice if the we passed in an astRoot here, but then we
	// would need to pass it to the XML structure nodes too...
	public JpaStructureNode getStructureNode(int offset) {
		CompilationUnit astRoot = this.buildASTRoot(); 
		
		if (this.contains(offset, astRoot)) {
			for (Iterator<JavaPersistentAttribute> stream = this.attributes(); stream.hasNext();) {
				JavaPersistentAttribute persistentAttribute = stream.next();
				if (persistentAttribute.contains(offset, astRoot)) {
					return persistentAttribute;
				}
			}
			return this;
		}
		return null;		
	}

	protected CompilationUnit buildASTRoot() {
		return JDTTools.buildASTRoot(this.resourcePersistentType.getJpaCompilationUnit().getCompilationUnit());
	}
	
	public boolean contains(int offset, CompilationUnit astRoot) {
		TextRange fullTextRange = this.getFullTextRange(astRoot);
		// 'fullTextRange' will be null if the type no longer exists in the java;
		// the context model can be out of synch with the resource model
		// when a selection event occurs before the context model has a
		// chance to synch with the resource model via the update thread
		return (fullTextRange == null) ? false : fullTextRange.includes(offset);
	}


	protected TextRange getFullTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentType.getTextRange(astRoot);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getSelectionTextRange(astRoot);
	}

	public TextRange getSelectionTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentType.getNameTextRange(astRoot);
	}
	
	public TextRange getSelectionTextRange() {
		return this.getSelectionTextRange(this.buildASTRoot());
	}
	
	
	public Iterator<PersistentType> inheritanceHierarchy() {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<PersistentType>(this) {
			@Override
			protected PersistentType nextLink(PersistentType pt) {
				return pt.getParentPersistentType();
			}
		};
	}

	public Iterator<PersistentType> ancestors() {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<PersistentType>(this.getParentPersistentType()) {
			@Override
			protected PersistentType nextLink(PersistentType pt) {
				return pt.getParentPersistentType();
			}
		};
	}

	public PersistentType getParentPersistentType() {
		return this.parentPersistentType;
	}

	public void setParentPersistentType(PersistentType newParentPersistentType) {
		if (attributeValueHasNotChanged(this.parentPersistentType, newParentPersistentType)) {
			return;
		}
		PersistentType oldParentPersistentType = this.parentPersistentType;
		this.parentPersistentType = newParentPersistentType;
		firePropertyChanged(PersistentType.PARENT_PERSISTENT_TYPE_PROPERTY, oldParentPersistentType, newParentPersistentType);
	}
	
	public boolean hasAnyAttributeMappingAnnotations() {
		if (this.resourcePersistentType.hasAnyAttributeAnnotations()) {
			return true;
		}
		return false;
	}
	
	// ******************** Updating **********************
	protected void initialize(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		this.parentPersistentType = this.parentPersistentType(jrpt);
		this.access = this.access(jrpt);
		this.name = this.name(jrpt);
		this.initializeMapping(jrpt);
		this.initializePersistentAttributes(jrpt);
	}
	
	protected void initializeMapping(JavaResourcePersistentType jrpt) {
		this.mapping  = getJpaPlatform().buildJavaTypeMappingFromAnnotation(this.javaMappingAnnotationName(jrpt), this);
		this.mapping.initialize(jrpt);
	}
	
	protected void initializePersistentAttributes(JavaResourcePersistentType jrpt) {
		Iterator<JavaResourcePersistentAttribute> resourceAttributes = jrpt.fields();
		if (getAccess() == AccessType.PROPERTY) {
			resourceAttributes = jrpt.properties();
		}		
		
		while (resourceAttributes.hasNext()) {
			this.attributes.add(createAttribute(resourceAttributes.next()));
		}
	}

	public void update(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		getJpaFile(this.resourcePersistentType.getResourceModel()).addRootStructureNode(this.resourcePersistentType.getQualifiedName(), this);
		updateParentPersistentType(jrpt);
		updateAccess(jrpt);
		updateName(jrpt);
		updateMapping(jrpt);
		updatePersistentAttributes(jrpt);
	}
	
	protected void updateAccess(JavaResourcePersistentType jrpt) {
		this.setAccess(this.access(jrpt));
	}

	/**
	 * Check the access "specified" by the java resource model.
	 * 		Check xml mapping specified access first
	 * 		If still null check java annotations if the xml is not metadata-complete = true
	 *		If still null then set to parentPersistentType access.
	 * 		If still null check entity-mappings specified access setting if this persistent-type is listed in an orm.xml file
	 * 		If still null check the persistence-unit default Access
	 * 		Default to FIELD if all else fails.
	 */
	protected AccessType access(JavaResourcePersistentType jrpt) {
		AccessType javaAccess = null;
		boolean metadataComplete = false;
		if (getOrmPersistentType() != null) {
			javaAccess = getOrmPersistentType().getMapping().getSpecifiedAccess();
			metadataComplete = getOrmPersistentType().getMapping().isMetadataComplete();
		}
		if (javaAccess == null && !metadataComplete) {
			javaAccess = AccessType.fromJavaResourceModel(jrpt.getAccess());
		}
		if (javaAccess == null) {
			if (getParentPersistentType() != null) {
				javaAccess = getParentPersistentType().getAccess();
			}
		}
		if (javaAccess == null) {
			if (getEntityMappings() != null) {
				javaAccess = getEntityMappings().getAccess();
			}
		}
		if (javaAccess == null) {
			//have to check persistence-unit separately in the case where it is not listed directly in an orm.xml
			//if it is listed in an orm.xml then the entityMappings().getAccess() check will cover persistence-unit.defaultAccess
			if (getPersistenceUnit() != null) {
				javaAccess = getPersistenceUnit().getDefaultAccess();
			}
		}
		if (javaAccess == null) {
			javaAccess = AccessType.FIELD;
		}
		return javaAccess;
	}
	
	protected void updateName(JavaResourcePersistentType jrpt) {
		this.setName(this.name(jrpt));	
	}
	
	protected String name(JavaResourcePersistentType jrpt) {
		return jrpt.getQualifiedName();
	}
	
	protected void updateMapping(JavaResourcePersistentType jrpt) {
		String javaMappingAnnotationName = this.javaMappingAnnotationName(jrpt);
		if (getMapping().getAnnotationName() != javaMappingAnnotationName) {
			setMapping(createJavaTypeMappingFromAnnotation(javaMappingAnnotationName, jrpt));
		}
		else {
			getMapping().update(jrpt);
		}
	}
	
	protected JavaTypeMapping createJavaTypeMappingFromMappingKey(String key) {
		return getJpaPlatform().buildJavaTypeMappingFromMappingKey(key, this);
	}
	
	protected JavaTypeMapping createJavaTypeMappingFromAnnotation(String annotationName, JavaResourcePersistentType jrpt) {
		JavaTypeMapping typeMapping = getJpaPlatform().buildJavaTypeMappingFromAnnotation(annotationName, this);
		typeMapping.initialize(jrpt);
		return typeMapping;
	}

	protected String javaMappingAnnotationName(JavaResourcePersistentType jrpt) {
		Annotation mappingAnnotation = (Annotation) jrpt.getMappingAnnotation();
		if (mappingAnnotation != null) {
			return mappingAnnotation.getAnnotationName();
		}
		return null;
	}

	protected void updatePersistentAttributes(JavaResourcePersistentType jrpt) {
		Iterator<JavaResourcePersistentAttribute> resourceAttributes = jrpt.fields();
		if (getAccess() == AccessType.PROPERTY) {
			resourceAttributes = jrpt.properties();
		}
		Collection<JavaPersistentAttribute> contextAttributesToRemove = CollectionTools.collection(attributes());
		Collection<JavaPersistentAttribute> contextAttributesToUpdate = new ArrayList<JavaPersistentAttribute>();
		int resourceIndex = 0;
		
		while (resourceAttributes.hasNext()) {
			JavaResourcePersistentAttribute resourceAttribute = resourceAttributes.next();
			boolean contextAttributeFound = false;
			for (JavaPersistentAttribute contextAttribute : contextAttributesToRemove) {
				if (contextAttribute.getResourcePersistentAttribute() == resourceAttribute) {
					moveAttribute(resourceIndex, contextAttribute);
					contextAttributesToRemove.remove(contextAttribute);
					contextAttributesToUpdate.add(contextAttribute);
					contextAttributeFound = true;
					break;
				}
			}
			if (!contextAttributeFound) {
				addAttribute(resourceIndex, createAttribute(resourceAttribute));
			}
			resourceIndex++;
		}
		for (JavaPersistentAttribute contextAttribute : contextAttributesToRemove) {
			removeAttribute(contextAttribute);
		}
		//first handle adding/removing of the persistent attributes, then update the others last, 
		//this causes less churn in the update process
		for (JavaPersistentAttribute contextAttribute : contextAttributesToUpdate) {
			contextAttribute.update();
		}
	}
	
	protected JavaPersistentAttribute createAttribute(JavaResourcePersistentAttribute jrpa) {
		return getJpaFactory().buildJavaPersistentAttribute(this, jrpa);
	}
	
	public void updateParentPersistentType(JavaResourcePersistentType jrpt) {
		setParentPersistentType(parentPersistentType(jrpt));
	}
	
	protected PersistentType parentPersistentType(JavaResourcePersistentType jrpt) {
		return parentPersistentType(jrpt.getSuperClassQualifiedName());
	}
	
	protected PersistentType parentPersistentType(String fullyQualifiedTypeName) {
		PersistentType possibleParent = possibleParent(fullyQualifiedTypeName);
		if (possibleParent == null) {
			return null;
		}
		if (possibleParent.isMapped()) {
			return possibleParent;
		}
		return possibleParent.getParentPersistentType();
	}

	/**
	 * JPA spec supports the case where there are non-persistent types in the hierarchy
	 * This will check for a PersistentType with the given name in this PersistenceUnit.
	 * If it is not found then find the JavaPersistentTypeResource and look for its parent type
	 */
	protected PersistentType possibleParent(String fullyQualifiedTypeName) {
		PersistentType possibleParent = getPersistentType(fullyQualifiedTypeName);
		if (possibleParent != null) {
			return possibleParent;
		}
		JavaResourcePersistentType jrpt = getJpaProject().getJavaResourcePersistentType(fullyQualifiedTypeName);
		return (jrpt == null) ? null : this.possibleParent(jrpt.getSuperClassQualifiedName());
	}
	
	protected PersistentType getPersistentType(String fullyQualifiedTypeName) {
		return getPersistenceUnit().getPersistentType(fullyQualifiedTypeName);
	}


	// ********** validation **********

	public void validate(List<IMessage> messages) {
		// build the AST root here to pass down
		this.validate(messages, this.buildASTRoot());	
	}
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		this.validateMapping(messages, astRoot);
		this.validateAttributes(messages, astRoot);
	}
	
	protected void validateMapping(List<IMessage> messages, CompilationUnit astRoot) {
		try {
			this.mapping.validate(messages, astRoot);
		} catch(Throwable t) {
			JptCorePlugin.log(t);
		}
	}
	
	protected void validateAttributes(List<IMessage> messages, CompilationUnit astRoot) {
		for (Iterator<JavaPersistentAttribute> stream = this.attributes(); stream.hasNext(); ) {
			this.validateAttribute(stream.next(), messages, astRoot);
		}
	}
	
	protected void validateAttribute(JavaPersistentAttribute attribute, List<IMessage> messages, CompilationUnit astRoot) {
		try {
			attribute.validate(messages, astRoot);
		} catch(Throwable t) {
			JptCorePlugin.log(t);
		}
	}
	

	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.name);
	}

	public void dispose() {
		JpaFile jpaFile = getJpaFile(this.resourcePersistentType.getResourceModel());
		
		if (jpaFile != null) {
			//jpaFile can be null if the .java file was deleted,
			//rootStructureNodes are cleared in the dispose of JpaFile
			jpaFile.removeRootStructureNode(this.resourcePersistentType.getQualifiedName());
		}
	}

}
