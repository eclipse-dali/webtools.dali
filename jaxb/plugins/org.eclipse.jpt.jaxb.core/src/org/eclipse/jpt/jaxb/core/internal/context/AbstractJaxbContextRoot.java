/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.TypeKind;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.jaxbindex.JaxbIndexResource;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * the context model root
 */
public abstract class AbstractJaxbContextRoot
		extends AbstractJaxbContextNode
		implements JaxbContextRoot {
	
	/* This object has no parent, so it must point to the JAXB project explicitly. */
	protected final JaxbProject jaxbProject;
	
	/* The map of package name to JaxbPackage objects */
	protected final Map<String, JaxbPackage> packages;
	
	/* The map of class name to JavaType objects */
	protected final Map<String, JavaType> types;
	
	
	public AbstractJaxbContextRoot(JaxbProject jaxbProject) {
		super(null);  // the JAXB project is not really a "parent"...
		if (jaxbProject == null) {
			throw new NullPointerException();
		}
		this.jaxbProject = jaxbProject;
		this.packages = new Hashtable<String, JaxbPackage>();
		this.types = new Hashtable<String, JavaType>();
		initialize();
	}
	
	
	@Override
	public JaxbContextRoot getContextRoot() {
		return this;
	}
	
	@Override
	protected boolean requiresParent() {
		return false;
	}
	
	protected void initialize() {
		
		// process packages with annotations first
		for (String pkg : calculateInitialPackageNames()) {
			this.packages.put(pkg, buildPackage(pkg));
		}
		
		// keep set of total types for remaining package initialization
		Set<String> totalTypeNames = new HashSet<String>();
		
		// process types with annotations and in jaxb.index files
		for (String typeName: CollectionTools.set(calculateInitialTypeNames())) {  // ensure iterable is unique
			totalTypeNames.add(typeName);
			addType_(buildType(typeName));
		}
		
		// once all classes have been processed, add packages
		for (String pkg : calculatePackageNames(totalTypeNames)) {
			if (! this.packages.containsKey(pkg)) {
				this.packages.put(pkg, buildPackage(pkg));
			}
		}
	}
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		for (JaxbPackage each : getPackages()) {
			each.synchronizeWithResourceModel();
		}
		for (JavaType each : getJavaTypes()) {
			each.synchronizeWithResourceModel();
		}
	}
	
	@Override
	public void update() {
		super.update();
		
		// keep a master list of these so that objects are updated only once
		final Set<String> packagesToUpdate = CollectionTools.<String>set();
		final Set<String> typesToUpdate = CollectionTools.<String>set();
		
		// keep a (shrinking) running list of these so that we know which ones we do eventually need to remove
		final Set<String> packagesToRemove = CollectionTools.set(this.packages.keySet());
		final Set<String> typesToRemove = CollectionTools.set(this.types.keySet());
		
		// keep a master list of all types that we've processed so we don't process them again
		final Set<String> totalTypes = CollectionTools.<String>set();
		
		// keep a running list of types that we need to scan for further referenced types
		final Set<String> typesToScan = CollectionTools.<String>set();
		
		// process packages with annotations first
		for (String pkg : calculateInitialPackageNames()) {
			if (this.packages.containsKey(pkg)) {
				packagesToUpdate.add(pkg);
				packagesToRemove.remove(pkg);
			}
			else {
				this.addPackage(this.buildPackage(pkg));
			}
		}
		
		// calculate initial types (annotated or listed in jaxb.index files)
		final Set<String> resourceTypesToProcess 
				= CollectionTools.set(calculateInitialTypeNames());
		
		// store set of types that are referenced (and should therefore be default mapped)
		final Set<String> referencedTypes = new HashSet<String>();
		
		// while there are resource types to process or types to scan, continue to do so
		while (! resourceTypesToProcess.isEmpty() || ! typesToScan.isEmpty()) {
			for (String typeName : IterableTools.cloneSnapshot(resourceTypesToProcess)) {
				JavaResourceAbstractType resourceType = getJaxbProject().getJavaResourceType(typeName);
				if (resourceType != null) { // if resource type is null, assume a validation error elsewhere
					typesToRemove.remove(typeName);
					totalTypes.add(typeName);
					typesToScan.add(typeName);
					processType(resourceType, typesToUpdate, referencedTypes.contains(typeName));
				}
				resourceTypesToProcess.remove(typeName);
			}
			
			for (String typeToScan : IterableTools.cloneSnapshot(typesToScan)) {
				JaxbTypeMapping typeMapping = getTypeMapping(typeToScan);
				if (typeMapping != null) {
					for (String referencedTypeName : typeMapping.getReferencedXmlTypeNames()) {
						if (! StringTools.isBlank(referencedTypeName) && ! totalTypes.contains(referencedTypeName)) {
							JavaResourceAbstractType referencedType = getJaxbProject().getJavaResourceType(referencedTypeName);
							if (referencedType != null && ! typeIsCoreJavaType(referencedTypeName)) {
								resourceTypesToProcess.add(referencedType.getTypeBinding().getQualifiedName());
							}
						}
						referencedTypes.add(referencedTypeName);
					}
				}
				typesToScan.remove(typeToScan);
			}
		}
		
		// once all classes have been processed, add packages
		for (String pkg : calculatePackageNames(totalTypes)) {
			if (this.packages.containsKey(pkg)) {
				packagesToUpdate.add(pkg);
				packagesToRemove.remove(pkg);
			}
			else {
				this.addPackage(this.buildPackage(pkg));
			}
		}
		
		for (String packageToUpdate : packagesToUpdate) {
			this.packages.get(packageToUpdate).update();
		}
		
		for (String typeToUpdate : typesToUpdate) {
			this.types.get(typeToUpdate).update();
		}
		
		for (String packageToRemove : packagesToRemove) {
			removePackage(packageToRemove);
		}
		
		for (String typeToRemove : typesToRemove) {
			removeType(typeToRemove);
		}
	}
	
	/**
	 * calculate set of packages that can be determined purely by presence of package annotations
	 */
	protected Set<String> calculateInitialPackageNames() {
		return CollectionTools.set(
				IterableTools.transform(getJaxbProject().getAnnotatedJavaResourcePackages(), JavaResourcePackage.NAME_TRANSFORMER));
	}
	
	/**
	 * calculate set of packages that can be determined from type names
	 */
	protected Set<String> calculatePackageNames(Set<String> typeNames) {
		Set<String> packageNames = CollectionTools.<String>set();
		for (String typeName : typeNames) {
			JavaType jaxbType = this.types.get(typeName);
			if (jaxbType != null) {
				packageNames.add(jaxbType.getTypeName().getPackageName());
			}
		}
		return packageNames;
	}
	
	/*
	 * Calculate iterable of initial type names
	 */
	protected Iterable<String> calculateInitialTypeNames() {
		return IterableTools.transform(
				calculateInitialTypes(), 
				new JavaResourceTypeNameTransformer());
	}
	
	protected class JavaResourceTypeNameTransformer
			implements Transformer<JavaResourceAbstractType, String> {
		
		public String transform(JavaResourceAbstractType input) {
			return input.getTypeBinding().getQualifiedName();
		}
	}
	
	/*
	 * Calculate iterable of initial types
	 * This should be:
	 * - all resource types with @XmlType, @XmlRootElement, or @XmlJavaTypeAdapter
	 * - all resource classes with @XmlRegistry
	 * - all resource enums with @XmlEnum
	 * - all types listed in jaxb.index files.
	 */
	@SuppressWarnings("unchecked")
	protected Iterable<JavaResourceAbstractType> calculateInitialTypes() {
		return IterableTools.concatenate(
				IterableTools.filter(getJaxbProject().getJavaSourceResourceTypes(), JAVA_RESOURCE_TYPE_IS_ANNOTATED),
				IterableTools.removeNulls(
						IterableTools.transform(
								IterableTools.children(getJaxbProject().getJaxbIndexResources(), JaxbIndexResource.CLASS_NAMES_TRANSFORMER),
								new JavaResourceTypeTransformer())));
	}
	
	public static final Predicate<JavaResourceAbstractType> JAVA_RESOURCE_TYPE_IS_ANNOTATED = new JavaResourceTypeIsAnnotated();
	public static class JavaResourceTypeIsAnnotated
		extends Predicate.Adapter<JavaResourceAbstractType>
	{
		@Override
		public boolean evaluate(JavaResourceAbstractType type) {
			if (type.getAstNodeType() == JavaResourceAbstractType.AstNodeType.TYPE) {
				if (type.getAnnotation(JAXB.XML_REGISTRY) != null) {
					return true;
				}
			}
			if (type.getAstNodeType() == JavaResourceAbstractType.AstNodeType.ENUM) {
				if (type.getAnnotation(JAXB.XML_ENUM) != null) {
					return true;
				}
			}
			return type.getAnnotation(JAXB.XML_TYPE) != null
					|| type.getAnnotation(JAXB.XML_ROOT_ELEMENT) != null
					|| type.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER) > 0;
		}
	}
	
	protected class JavaResourceTypeTransformer
			extends TransformerAdapter<String, JavaResourceAbstractType> {
		@Override
		public JavaResourceAbstractType transform(String typeName) {
			return getJaxbProject().getJavaResourceType(typeName);
		}
	}
	
	private boolean typeIsCoreJavaType(String typeName){
		return typeName.startsWith(JAXB.CORE_JAVA_TYPE_PACKAGE_PREFIX);
	}
	
	protected void processType(JavaResourceAbstractType resourceType, Set<String> typesToUpdate, boolean defaultMapped) {
		TypeKind jaxbTypeKind = calculateJaxbTypeKind(resourceType);
		String className = resourceType.getTypeBinding().getQualifiedName();
		
		if (this.types.containsKey(className)) {
			JavaType type = this.types.get(className);
			if (type.getKind() == jaxbTypeKind) {
				typesToUpdate.add(className);
				type.setDefaultMapped(defaultMapped);
				return;
			}
			this.removeType(className); // this will remove a type of another kind
		}
		
		JavaType type = buildType(resourceType);
		type.setDefaultMapped(defaultMapped);
		this.addType(type);
	}
	
	protected TypeKind calculateJaxbTypeKind(JavaResourceAbstractType resourceType) {
		if (resourceType.getAstNodeType() == JavaResourceAbstractType.AstNodeType.ENUM) {
			return TypeKind.ENUM;
		}
		// else is of kind CLASS
		return TypeKind.CLASS;
	}
	
	protected JavaType buildType(String typeName) {
		JavaResourceAbstractType resourceType = getJaxbProject().getJavaResourceType(typeName);
		return (resourceType == null) ? null : buildType(resourceType);
	}
	
	protected JavaType buildType(JavaResourceAbstractType resourceType) {
		return (calculateJaxbTypeKind(resourceType) == TypeKind.ENUM) ?
				buildJaxbEnum((JavaResourceEnum) resourceType) :
				buildJaxbClass((JavaResourceType) resourceType);
	}
	
	
	// ***** AbstractJaxbNode overrides ***** 
	
	@Override
	public JaxbProject getJaxbProject() {
		return this.jaxbProject;
	}
	
	@Override
	public IResource getResource() {
		return this.getProject();
	}
	
	protected IProject getProject() {
		return this.jaxbProject.getProject();
	}
	
	
	// ***** packages *****
	
	public Iterable<JaxbPackage> getPackages() {
		return IterableTools.cloneLive(this.packages.values());
	}
	
	public int getPackagesSize() {
		return this.packages.size();
	}

	public JaxbPackage getPackage(String packageName) {
		for (JaxbPackage jaxbPackage : this.getPackages()) {
			if (ObjectTools.equals(jaxbPackage.getName(), packageName)) {
				return jaxbPackage;
			}
		}
		return null;
	}
	
	protected JaxbPackage addPackage(JaxbPackage contextPackage) {
		if (this.packages.containsKey(contextPackage.getName())) {
			throw new IllegalArgumentException("Package with that name already exists."); //$NON-NLS-1$
		}
		this.packages.put(contextPackage.getName(), contextPackage);
		fireItemAdded(PACKAGES_COLLECTION, contextPackage);
		return contextPackage;
	}
	
	protected void removePackage(JaxbPackage contextPackage) {
		this.removePackage(contextPackage.getName());
	}
	
	protected void removePackage(String packageName) {
		if (! this.packages.containsKey(packageName)) {
			throw new IllegalArgumentException("No package with that name exists."); //$NON-NLS-1$
		}
		JaxbPackage removedPackage = this.packages.remove(packageName);
		fireItemRemoved(PACKAGES_COLLECTION, removedPackage);
	}
	
	protected JaxbPackage buildPackage(String packageName) {
		return this.getFactory().buildPackage(this, packageName);
	}
	
	protected boolean isEmpty(JaxbPackage jaxbPackage) {
		return jaxbPackage.isEmpty();
	}
	
	
	// ***** types *****
	
	public Iterable<JavaType> getJavaTypes() {
		return IterableTools.cloneLive(this.types.values());
	}
	
	public int getJavaTypesSize() {
		return this.types.size();
	}
	
	public JavaType getJavaType(String typeName) {
		return typeName == null ? null : this.types.get(typeName);
	}
	
	protected void addType_(JavaType type) {
		this.types.put(type.getTypeName().getFullyQualifiedName(), type);
	}
	
	protected void addType(JavaType type) {
		if (this.types.containsKey(type.getTypeName().getFullyQualifiedName())) {
			throw new IllegalArgumentException("Type with that name already exists."); //$NON-NLS-1$
		}
		addType_(type);
		fireItemAdded(JAVA_TYPES_COLLECTION, type);
	}
	
	protected void removeType(JavaType type) {
		removeType(type.getTypeName().getFullyQualifiedName());
	}
	
	protected void removeType(String typeName) {
		if (! this.types.containsKey(typeName)) {
			throw new IllegalArgumentException("No type with that name exists."); //$NON-NLS-1$
		}
		JavaType removedType = this.types.remove(typeName);
		fireItemRemoved(JAVA_TYPES_COLLECTION, removedType);
	}
	
	public Iterable<JavaType> getJavaTypes(final JaxbPackage jaxbPackage) {
		return IterableTools.filter(getJavaTypes(), new JavaTypeIsInPackage(jaxbPackage));
	}

	public static class JavaTypeIsInPackage
		extends Predicate.Adapter<JavaType>
	{
		private final JaxbPackage jaxbPackage;
		public JavaTypeIsInPackage(JaxbPackage jaxbPackage) {
			super();
			this.jaxbPackage = jaxbPackage;
		}
		@Override
		public boolean evaluate(JavaType javaType) {
			return javaType.getTypeName().getPackageName().equals(this.jaxbPackage.getName());
		}
	}
	
	public Iterable<JavaClass> getJavaClasses() {
		return IterableTools.downCast(IterableTools.filter(getJavaTypes(), new JavaType.IsKind(TypeKind.CLASS)));
	}
	
	public Iterable<JavaClass> getJavaClasses(JaxbPackage jaxbPackage) {
		return IterableTools.downCast(IterableTools.filter(getJavaTypes(jaxbPackage), new JavaType.IsKind(TypeKind.CLASS)));
	}
	
	public Iterable<JavaEnum> getJavaEnums() {
		return IterableTools.downCast(IterableTools.filter(getJavaTypes(), new JavaType.IsKind(TypeKind.ENUM)));
	}
	
	public Iterable<JavaEnum> getJavaEnums(JaxbPackage jaxbPackage) {
		return IterableTools.downCast(IterableTools.filter(getJavaTypes(jaxbPackage), new JavaType.IsKind(TypeKind.ENUM)));
	}
	
	
	protected JavaClass buildJaxbClass(JavaResourceType resourceType) {
		return this.getFactory().buildJaxbClass(this, resourceType);
	}
	
	protected JavaEnum buildJaxbEnum(JavaResourceEnum resourceEnum) {
		return this.getFactory().buildJaxbEnum(this, resourceEnum);
	}
	
	public Iterable<XmlRegistry> getXmlRegistries(JaxbPackage jaxbPackage) {
		return IterableTools.removeNulls(
				IterableTools.transform(getJavaClasses(jaxbPackage), 
				JavaClass.XML_REGISTRY_TRANSFORMER));
	}
	
	public JaxbTypeMapping getTypeMapping(String typeName) {
		JavaType type = getJavaType(typeName);
		return (type == null) ? null : type.getMapping();
	}
	
	public JaxbClassMapping getClassMapping(String typeName) {
		JavaType type = getJavaType(typeName);
		return (type == null || (type.getKind() != TypeKind.CLASS)) ? 
				null : ((JavaClass) type).getMapping();
	}
	
	
	@Override
    public void stateChanged() {
		super.stateChanged();
		// forward to JAXB project
		this.jaxbProject.stateChanged();
    }
	
	
	// **************** validation ********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		for (JaxbPackage pkg : this.packages.values()) {
			pkg.validate(messages, reporter);
		}
		for (JavaType type : this.types.values()) {
			type.validate(messages, reporter);
		}
	}
}
