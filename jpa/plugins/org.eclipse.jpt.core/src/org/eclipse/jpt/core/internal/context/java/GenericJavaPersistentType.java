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
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaStructureNodes;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
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

	protected JavaResourcePersistentType persistentTypeResource;

	public GenericJavaPersistentType(JpaContextNode parent) {
		super(parent);
		this.attributes = new ArrayList<JavaPersistentAttribute>();
	}
	
	public String getId() {
		return JavaStructureNodes.PERSISTENT_TYPE_ID;
	}
	
	public void initializeFromResource(JavaResourcePersistentType persistentTypeResource) {
		this.persistentTypeResource = persistentTypeResource;
		this.parentPersistentType = this.parentPersistentType(persistentTypeResource);
		this.access = this.access(persistentTypeResource);
		this.name = this.name(persistentTypeResource);
		this.initializeMapping(persistentTypeResource);
		this.initializePersistentAttributes(persistentTypeResource);
	}
	
	protected void initializeMapping(JavaResourcePersistentType persistentTypeResource) {
		this.mapping  = jpaPlatform().buildJavaTypeMappingFromAnnotation(this.javaMappingAnnotationName(persistentTypeResource), this);
		this.mapping.initializeFromResource(persistentTypeResource);
	}
	
	protected void initializePersistentAttributes(JavaResourcePersistentType persistentTypeResource) {
		Iterator<JavaResourcePersistentAttribute> resourceAttributes = persistentTypeResource.fields();
		if (access() == AccessType.PROPERTY) {
			resourceAttributes = persistentTypeResource.properties();
		}		
		
		while (resourceAttributes.hasNext()) {
			this.attributes.add(createAttribute(resourceAttributes.next()));
		}
	}
	
	@Override
	public IResource resource() {
		return this.persistentTypeResource.resourceModel().resource().getCompilationUnit().getResource();
	}

	
	//****************** IPersistentType implementation *******************
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

	public String mappingKey() {
		return getMapping().getKey();
	}
	
	public void setMappingKey(String key) {
		if (key == getMapping().getKey()) {
			return;
		}
		JavaTypeMapping oldMapping = getMapping();
		JavaTypeMapping newMapping = createJavaTypeMappingFromMappingKey(key);
	
		this.mapping = newMapping;	
		this.persistentTypeResource.setMappingAnnotation(newMapping.annotationName());
		firePropertyChanged(PersistentType.MAPPING_PROPERTY, oldMapping, newMapping);
	
		if (oldMapping != null) {
			Collection<String> annotationsToRemove = CollectionTools.collection(oldMapping.correspondingAnnotationNames());
			if (getMapping() != null) {
				CollectionTools.removeAll(annotationsToRemove, getMapping().correspondingAnnotationNames());
			}
			
			for (String annotationName : annotationsToRemove) {
				this.persistentTypeResource.removeAnnotation(annotationName);
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

	public AccessType access() {
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

	public JavaPersistentAttribute attributeNamed(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = attributesNamed(attributeName);
		return (stream.hasNext()) ? stream.next() : null;
	}

	public PersistentAttribute resolveAttribute(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = attributesNamed(attributeName);
		if (stream.hasNext()) {
			JavaPersistentAttribute attribute = stream.next();
			return (stream.hasNext()) ? null /*more than one*/: attribute;
		}
		return (parentPersistentType() == null) ? null : parentPersistentType().resolveAttribute(attributeName);
	}
	
	public ListIterator<JavaPersistentAttribute> attributes() {
		return new CloneListIterator<JavaPersistentAttribute>(this.attributes);
	}
	
	public int attributesSize() {
		return this.attributes.size();
	}
	
	private void addAttribute(JavaPersistentAttribute attribute) {
		addItemToList(attribute, this.attributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}

	private void removeAttribute(JavaPersistentAttribute attribute) {
		removeItemFromList(attribute, this.attributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
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
	
	public JpaStructureNode structureNode(int offset) {
		//TODO astRoot, possibly get this instead of rebuilding it
		CompilationUnit astRoot = this.persistentTypeResource.getMember().astRoot(); 
		
		for (Iterator<JavaPersistentAttribute> i = attributes(); i.hasNext();) {
			JavaPersistentAttribute persistentAttribute = i.next();
			if (persistentAttribute.contains(offset, astRoot)) {
				return persistentAttribute;
			}
		}
		
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
		return this.persistentTypeResource.textRange(astRoot);
	}

	public TextRange validationTextRange(CompilationUnit astRoot) {
		return this.selectionTextRange(astRoot);
	}

	public TextRange selectionTextRange(CompilationUnit astRoot) {
		return this.persistentTypeResource.nameTextRange(astRoot);
	}
	
	public TextRange selectionTextRange() {
		return this.selectionTextRange(this.persistentTypeResource.getMember().astRoot());
	}
	
	
	public Iterator<PersistentType> inheritanceHierarchy() {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<PersistentType>(this) {
			@Override
			protected PersistentType nextLink(PersistentType pt) {
				return pt.parentPersistentType();
			}
		};
	}

	public PersistentType parentPersistentType() {
		return this.parentPersistentType;
	}

	public boolean hasAnyAttributeMappingAnnotations() {
		if (this.persistentTypeResource.hasAnyAttributeAnnotations()) {
			return true;
		}
		return false;
	}
	
	// ******************** Updating **********************
	public void update(JavaResourcePersistentType persistentTypeResource) {
		this.persistentTypeResource = persistentTypeResource;
		this.persistentTypeResource.resourceModel().addRootStructureNode(this);
		updateParentPersistentType(persistentTypeResource);
		updateAccess(persistentTypeResource);
		updateName(persistentTypeResource);
		updateMapping(persistentTypeResource);
		updatePersistentAttributes(persistentTypeResource);
	}
	
	protected void updateAccess(JavaResourcePersistentType persistentTypeResource) {
		this.setAccess(this.access(persistentTypeResource));
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
	protected AccessType access(JavaResourcePersistentType persistentTypeResource) {
		AccessType javaAccess = null;
		boolean metadataComplete = false;
		if (ormPersistentType() != null) {
			javaAccess = ormPersistentType().getMapping().getSpecifiedAccess();
			metadataComplete = ormPersistentType().getMapping().isMetadataComplete();
		}
		if (javaAccess == null && !metadataComplete) {
			javaAccess = AccessType.fromJavaResourceModel(persistentTypeResource.getAccess());
		}
		if (javaAccess == null) {
			if (parentPersistentType() != null) {
				javaAccess = parentPersistentType().access();
			}
		}
		if (javaAccess == null) {
			if (entityMappings() != null) {
				javaAccess = entityMappings().getAccess();
			}
		}
		if (javaAccess == null) {
			//have to check persistence-unit separately in the case where it is not listed directly in an orm.xml
			//if it is listed in an orm.xml then the entityMappings().getAccess() check will cover persistence-unit.defaultAccess
			if (persistenceUnit() != null) {
				javaAccess = persistenceUnit().getDefaultAccess();
			}
		}
		if (javaAccess == null) {
			javaAccess = AccessType.FIELD;
		}
		return javaAccess;
	}
	
	protected void updateName(JavaResourcePersistentType persistentTypeResource) {
		this.setName(this.name(persistentTypeResource));	
	}
	
	protected String name(JavaResourcePersistentType persistentTypeResource) {
		return persistentTypeResource.getQualifiedName();
	}
	
	protected void updateMapping(JavaResourcePersistentType persistentTypeResource) {
		String javaMappingAnnotationName = this.javaMappingAnnotationName(persistentTypeResource);
		if (getMapping().annotationName() != javaMappingAnnotationName) {
			setMapping(createJavaTypeMappingFromAnnotation(javaMappingAnnotationName, persistentTypeResource));
		}
		else {
			getMapping().update(persistentTypeResource);
		}
	}
	
	protected JavaTypeMapping createJavaTypeMappingFromMappingKey(String key) {
		return jpaPlatform().buildJavaTypeMappingFromMappingKey(key, this);
	}
	
	protected JavaTypeMapping createJavaTypeMappingFromAnnotation(String annotationName, JavaResourcePersistentType persistentTypeResource) {
		JavaTypeMapping mapping = jpaPlatform().buildJavaTypeMappingFromAnnotation(annotationName, this);
		mapping.initializeFromResource(persistentTypeResource);
		return mapping;
	}

	protected String javaMappingAnnotationName(JavaResourcePersistentType typeResource) {
		Annotation mappingAnnotation = (Annotation) typeResource.mappingAnnotation();
		if (mappingAnnotation != null) {
			return mappingAnnotation.getAnnotationName();
		}
		return null;
	}

	protected void updatePersistentAttributes(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaPersistentAttribute> contextAttributes = attributes();
		Iterator<JavaResourcePersistentAttribute> resourceAttributes = persistentTypeResource.fields();
		if (access() == AccessType.PROPERTY) {
			resourceAttributes = persistentTypeResource.properties();
		}		
		
		while (contextAttributes.hasNext()) {
			JavaPersistentAttribute persistentAttribute = contextAttributes.next();
			if (resourceAttributes.hasNext()) {
				persistentAttribute.update(resourceAttributes.next());
			}
			else {
				removeAttribute(persistentAttribute);
			}
		}
		
		while (resourceAttributes.hasNext()) {
			addAttribute(createAttribute(resourceAttributes.next()));
		}
	}
	
	protected JavaPersistentAttribute createAttribute(JavaResourcePersistentAttribute persistentAttributeResource) {
		JavaPersistentAttribute javaPersistentAttribute = jpaFactory().buildJavaPersistentAttribute(this);
		javaPersistentAttribute.initializeFromResource(persistentAttributeResource);
		return javaPersistentAttribute;
	}
	
	public void updateParentPersistentType(JavaResourcePersistentType persistentTypeResource) {
		//TODO do we need any change notification for this?
		this.parentPersistentType = parentPersistentType(persistentTypeResource);
	}
	
	protected PersistentType parentPersistentType(JavaResourcePersistentType persistentTypeResource) {
		return parentPersistentType(persistentTypeResource.getSuperClassQualifiedName());
	}
	
	protected PersistentType parentPersistentType(String fullyQualifiedTypeName) {
		PersistentType possibleParent = possibleParent(fullyQualifiedTypeName);
		if (possibleParent == null) {
			return null;
		}
		if (possibleParent.isMapped()) {
			return possibleParent;
		}
		return possibleParent.parentPersistentType();
	}

	/**
	 * JPA spec supports the case where there are non-persistent types in the hierarchy
	 * This will check for a PersistentType with the given name in this PersistenceUnit.
	 * If it is not found then find the JavaPersistentTypeResource and look for its parent type
	 */
	protected PersistentType possibleParent(String fullyQualifiedTypeName) {
		PersistentType possibleParent = persistentType(fullyQualifiedTypeName);
		if (possibleParent != null) {
			return possibleParent;
		}
		JavaResourcePersistentType javaPersistentTypeResource = jpaProject().javaPersistentTypeResource(fullyQualifiedTypeName);
		if (javaPersistentTypeResource != null) {
			return possibleParent(javaPersistentTypeResource.getSuperClassQualifiedName());
		}
		return null;		
	}
	
	protected PersistentType persistentType(String fullyQualifiedTypeName) {
		return persistenceUnit().persistentType(fullyQualifiedTypeName);
	}

	//*************** Validation ******************************************
	public void addToMessages(List<IMessage> messages) {
		//get astRoot here to pass down
		addToMessages(messages, this.persistentTypeResource.getMember().astRoot());	
	}
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		this.mapping.addToMessages(messages, astRoot);	
		addAttributeMessages(messages, astRoot);
	}
	
	protected void addAttributeMessages(List<IMessage> messages, CompilationUnit astRoot) {
		for (JavaPersistentAttribute attributeContext : this.attributes) {
			attributeContext.addToMessages(messages, astRoot);
		}
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}

}