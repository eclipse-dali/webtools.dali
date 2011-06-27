/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SnapshotCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.context.JaxbTransientClass;
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.jaxbindex.JaxbIndexResource;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * the context model root
 */
public class AbstractJaxbContextRoot
		extends AbstractJaxbContextNode
		implements JaxbContextRoot {
	
	/* This object has no parent, so it must point to the JAXB project explicitly. */
	protected final JaxbProject jaxbProject;
	
	/* The map of package name to JaxbPackage objects */
	protected final Map<String, JaxbPackage> packages;
	
	/* The map of class name to JaxbType objects */
	protected final Map<String, JaxbType> types;
	
	
	public AbstractJaxbContextRoot(JaxbProject jaxbProject) {
		super(null);  // the JAXB project is not really a "parent"...
		if (jaxbProject == null) {
			throw new NullPointerException();
		}
		this.jaxbProject = jaxbProject;
		this.packages = new HashMap<String, JaxbPackage>();
		this.types = new HashMap<String, JaxbType>();
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
		// keep a master list of all types that we've processed so we don't process them again
		final Set<String> totalTypes = CollectionTools.<String>set();
		
		// keep an running list of types that we need to scan for further referenced types
		final Set<String> typesToScan = CollectionTools.<String>set();
		
		// process packages with annotations first
		for (String pkg : calculateInitialPackageNames()) {
			this.packages.put(pkg, buildPackage(pkg));
		}
		
		// process registry classes before other classes (they are completely determined by annotation)
		for (JavaResourceType registryResourceType : calculateRegistries()) {
			String className = registryResourceType.getQualifiedName();
			totalTypes.add(className);
			typesToScan.add(className);
			addType_(buildRegistry(registryResourceType));
		}
		
		// calculate initial set of persistent types (annotated with @XmlType or listed in jaxb.index files)
		final Set<JavaResourceAbstractType> resourceTypesToProcess = calculateInitialPersistentTypes();
		
		// while there are resource types to process or types to scan, continue to do so
		while (! resourceTypesToProcess.isEmpty() || ! typesToScan.isEmpty()) {
			for (JavaResourceAbstractType resourceType : new SnapshotCloneIterable<JavaResourceAbstractType>(resourceTypesToProcess)) {
				String className = resourceType.getQualifiedName();
				totalTypes.add(className);
				typesToScan.add(className);
				JaxbType.Kind jaxbTypeKind = calculateJaxbTypeKind(resourceType);
				addType_(buildType(jaxbTypeKind, resourceType));
				resourceTypesToProcess.remove(resourceType);
			}
			
			for (String typeToScan : new SnapshotCloneIterable<String>(typesToScan)) {
				JaxbType jaxbType = getType(typeToScan);
				if (jaxbType != null) {
					for (String referencedTypeName : jaxbType.getDirectlyReferencedTypeNames()) {
						if (! totalTypes.contains(referencedTypeName)) {
							JavaResourceAbstractType referencedType = getJaxbProject().getJavaResourceType(referencedTypeName);
							if (referencedType != null) {
								resourceTypesToProcess.add(referencedType);
							}
						}
					}
				}
				typesToScan.remove(typeToScan);
			}
		}
		
		// once all classes have been processed, add packages
		for (String pkg : calculatePackageNames(totalTypes)) {
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
		for (JaxbType each : getTypes()) {
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
		
		// keep an running list of types that we need to scan for further referenced types
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
		
		// process registry classes before other classes (they are completely determined by annotation)
		for (JavaResourceType registryResourceType : calculateRegistries()) {
			String className = registryResourceType.getQualifiedName();
			typesToRemove.remove(className);
			totalTypes.add(className);
			typesToScan.add(className);
			if (this.types.containsKey(className)) {
				if (this.types.get(className).getKind() == JaxbType.Kind.REGISTRY) {
					typesToUpdate.add(className);
				}
				else {
					this.removeType(className); // this will remove a type of another kind
					this.addType(buildRegistry(registryResourceType));
				}
			}
			else {
				this.addType(buildRegistry(registryResourceType));
			}
		}
		
		// calculate initial set of persistent types (annotated with @XmlType or listed in jaxb.index files)
		final Set<JavaResourceAbstractType> resourceTypesToProcess = calculateInitialPersistentTypes();
		
		// while there are resource types to process or types to scan, continue to do so
		while (! resourceTypesToProcess.isEmpty() || ! typesToScan.isEmpty()) {
			for (JavaResourceAbstractType resourceType : new SnapshotCloneIterable<JavaResourceAbstractType>(resourceTypesToProcess)) {
				String className = resourceType.getQualifiedName();
				typesToRemove.remove(className);
				totalTypes.add(className);
				typesToScan.add(className);
				processType(resourceType, typesToUpdate);
				resourceTypesToProcess.remove(resourceType);
			}
			
			for (String typeToScan : new SnapshotCloneIterable<String>(typesToScan)) {
				JaxbType jaxbType = getType(typeToScan);
				if (jaxbType != null) {
					for (String referencedTypeName : jaxbType.getDirectlyReferencedTypeNames()) {
						if (! StringTools.stringIsEmpty(referencedTypeName) && ! totalTypes.contains(referencedTypeName)) {
							JavaResourceAbstractType referencedType = getJaxbProject().getJavaResourceType(referencedTypeName);
							if (referencedType != null) {
								resourceTypesToProcess.add(referencedType);
							}
						}
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
				new TransformationIterable<JavaResourcePackage, String>(
						getJaxbProject().getAnnotatedJavaResourcePackages()) {
					@Override
					protected String transform(JavaResourcePackage o) {
						return o.getName();
					}
				});
	}
	
	/**
	 * calculate set of packages that can be determined from type names
	 */
	protected Set<String> calculatePackageNames(Set<String> typeNames) {
		Set<String> packageNames = CollectionTools.<String>set();
		for (String typeName : typeNames) {
			JaxbType jaxbType = this.types.get(typeName);
			if (jaxbType != null) {
				packageNames.add(jaxbType.getPackageName());
			}
		}
		return packageNames;
	}
	
	/*
	 * Calculate set of registries
	 * (this should be all resource types with the @XmlRegistry annotation)
	 */
	protected Set<JavaResourceType> calculateRegistries() {
		return CollectionTools.set(
				new SubIterableWrapper<JavaResourceAbstractType, JavaResourceType>(
					new FilteringIterable<JavaResourceAbstractType>(
							getJaxbProject().getJavaSourceResourceTypes()) {
						@Override
						protected boolean accept(JavaResourceAbstractType o) {
							return o.getKind() == JavaResourceAbstractType.Kind.TYPE 
									&& o.getAnnotation(JAXB.XML_REGISTRY) != null;
						}
					}));
	}
	
	/*
	 * Calculate set of resource types annotated with @XmlType (and not @XmlRegistry)
	 * plus those referred to in jaxb.index files
	 */
	protected Set<JavaResourceAbstractType> calculateInitialPersistentTypes() {
		Set<JavaResourceAbstractType> set = CollectionTools.set(
				new FilteringIterable<JavaResourceAbstractType>(getJaxbProject().getJavaSourceResourceTypes()) {
					@Override
					protected boolean accept(JavaResourceAbstractType o) {
						return o.getAnnotation(JAXB.XML_TYPE) != null && o.getAnnotation(JAXB.XML_REGISTRY) == null;
					}
				});
		CollectionTools.addAll(
				set,
				new FilteringIterable<JavaResourceAbstractType>(
						new TransformationIterable<String, JavaResourceAbstractType>(
								new CompositeIterable<String>(
										new TransformationIterable<JaxbIndexResource, Iterable<String>>(getJaxbProject().getJaxbIndexResources()) {
											@Override
											protected Iterable<String>transform(JaxbIndexResource o) {
												return o.getFullyQualifiedClassNames();
											}
										})) {
							@Override
							protected JavaResourceAbstractType transform(String o) {
								return getJaxbProject().getJavaResourceType(o);
							}
						},
						NotNullFilter.<JavaResourceAbstractType>instance()));
		return set;
	}
	
	protected void processType(JavaResourceAbstractType resourceType, Set<String> typesToUpdate) {
		JaxbType.Kind jaxbTypeKind = calculateJaxbTypeKind(resourceType);
		String className = resourceType.getQualifiedName();
		
		if (this.types.containsKey(className)) {
			if (this.types.get(className).getKind() == jaxbTypeKind) {
				typesToUpdate.add(className);
				return;
			}
			else {
				this.removeType(className); // this will remove a type of another kind
			}
		}
		
		this.addType(buildType(jaxbTypeKind, resourceType));
	}
	
	protected JaxbType.Kind calculateJaxbTypeKind(JavaResourceAbstractType resourceType) {
		if (resourceType.getKind() == JavaResourceAbstractType.Kind.ENUM) {
			return JaxbType.Kind.PERSISTENT_ENUM;
		}
		// else is of kind TYPE
		else if (resourceType.getAnnotation(JAXB.XML_REGISTRY) != null) {
			return JaxbType.Kind.REGISTRY;
		}
		else if (resourceType.getAnnotation(JAXB.XML_TRANSIENT) != null) {
			return JaxbType.Kind.TRANSIENT;
		}
		else {
			return JaxbType.Kind.PERSISTENT_CLASS;
		}
	}
	
	protected JaxbType buildType(JaxbType.Kind jaxbTypeKind, JavaResourceAbstractType resourceType) {
		if (jaxbTypeKind == JaxbType.Kind.PERSISTENT_ENUM) {
			return buildPersistentEnum((JavaResourceEnum) resourceType);
		}
		else if (jaxbTypeKind == JaxbType.Kind.REGISTRY) {
			return buildRegistry((JavaResourceType) resourceType);
		}
		else if (jaxbTypeKind == JaxbType.Kind.TRANSIENT) {
			return buildTransientClass((JavaResourceType) resourceType);
		}
		else {
			return buildPersistentClass((JavaResourceType) resourceType);
		}
	}
	
	
	// ********** AbstractJaxbNode overrides **********
	
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
	
	
	// ************* packages ***************
	
	public Iterable<JaxbPackage> getPackages() {
		return new LiveCloneIterable<JaxbPackage>(this.packages.values());
	}
	
	public int getPackagesSize() {
		return this.packages.size();
	}

	public JaxbPackage getPackage(String packageName) {
		for (JaxbPackage jaxbPackage : this.getPackages()) {
			if (StringTools.stringsAreEqual(jaxbPackage.getName(), packageName)) {
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
	
	
	// ********** types ***********
	
	public Iterable<JaxbType> getTypes() {
		return new LiveCloneIterable<JaxbType>(this.types.values());
	}
	
	public int getTypesSize() {
		return this.types.size();
	}
	
	public JaxbType getType(String typeName) {
		return this.types.get(typeName);
	}
	
	protected void addType_(JaxbType type) {
		this.types.put(type.getFullyQualifiedName(), type);
	}
	
	protected void addType(JaxbType type) {
		if (this.types.containsKey(type.getFullyQualifiedName())) {
			throw new IllegalArgumentException("Type with that name already exists."); //$NON-NLS-1$
		}
		addType_(type);
		fireItemAdded(TYPES_COLLECTION, type);
	}
	
	protected void removeType(JaxbType type) {
		removeType(type.getFullyQualifiedName());
	}
	
	protected void removeType(String typeName) {
		if (! this.types.containsKey(typeName)) {
			throw new IllegalArgumentException("No type with that name exists."); //$NON-NLS-1$
		}
		JaxbType removedType = this.types.remove(typeName);
		fireItemRemoved(TYPES_COLLECTION, removedType);
	}
	
	public Iterable<JaxbType> getTypes(final JaxbPackage jaxbPackage) {
		return new FilteringIterable<JaxbType>(getTypes()) {
			@Override
			protected boolean accept(JaxbType o) {
				return o.getPackageName().equals(jaxbPackage.getName());
			}
		};
	}
	
	
	// ********** registries **********
	
	public Iterable<JaxbRegistry> getRegistries() {
		return new SubIterableWrapper<JaxbType, JaxbRegistry>(
				new FilteringIterable<JaxbType>(getTypes()) {
					@Override
					protected boolean accept(JaxbType o) {
						return o.getKind() == JaxbType.Kind.REGISTRY;
					}
				});
	}
	
	protected JaxbRegistry buildRegistry(JavaResourceType resourceType) {
		return this.getFactory().buildRegistry(this, resourceType);
	}
	
	public Iterable<JaxbRegistry> getRegistries(final JaxbPackage jaxbPackage) {
		return new FilteringIterable<JaxbRegistry>(getRegistries()) {
			@Override
			protected boolean accept(JaxbRegistry o) {
				return o.getPackageName().equals(jaxbPackage.getName());
			}
		};
	}


	// ********** transient types **********
	
	public Iterable<JaxbTransientClass> getTransientClasses() {
		return new SubIterableWrapper<JaxbType, JaxbTransientClass>(
				new FilteringIterable<JaxbType>(getTypes()) {
					@Override
					protected boolean accept(JaxbType o) {
						return o.getKind() == JaxbType.Kind.TRANSIENT;
					}
				});
	}
	
	protected JaxbTransientClass buildTransientClass(JavaResourceType resourceType) {
		return this.getFactory().buildJavaTransientClass(this, resourceType);
	}
	
	public Iterable<JaxbTransientClass> getTransientClasses(final JaxbPackage jaxbPackage) {
		return new FilteringIterable<JaxbTransientClass>(getTransientClasses()) {
			@Override
			protected boolean accept(JaxbTransientClass o) {
				return o.getPackageName().equals(jaxbPackage.getName());
			}
		};
	}

	public JaxbTransientClass getTransientClass(String className) {
		for (JaxbTransientClass jaxbClass : this.getTransientClasses()) {
			if (StringTools.stringsAreEqual(jaxbClass.getFullyQualifiedName(), className)) {
				return jaxbClass;
			}
		}
		return null;
	}

	// ********** persistent classes **********
	
	public Iterable<JaxbPersistentClass> getPersistentClasses() {
		return new SubIterableWrapper<JaxbType, JaxbPersistentClass>(
				new FilteringIterable<JaxbType>(getTypes()) {
					@Override
					protected boolean accept(JaxbType o) {
						return o.getKind() == JaxbType.Kind.PERSISTENT_CLASS;
					}
				});
	}
	
	protected JaxbPersistentClass buildPersistentClass(JavaResourceType resourceType) {
		return this.getFactory().buildJavaPersistentClass(this, resourceType);
	}
	
	public Iterable<JaxbPersistentClass> getPersistentClasses(final JaxbPackage jaxbPackage) {
		return new FilteringIterable<JaxbPersistentClass>(getPersistentClasses()) {
			@Override
			protected boolean accept(JaxbPersistentClass o) {
				return o.getPackageName().equals(jaxbPackage.getName());
			}
		};
	}

	public JaxbPersistentClass getPersistentClass(String className) {
		for (JaxbPersistentClass jaxbClass : this.getPersistentClasses()) {
			if (StringTools.stringsAreEqual(jaxbClass.getFullyQualifiedName(), className)) {
				return jaxbClass;
			}
		}
		return null;
	}

	public JaxbClass getClass(String fullyQualifiedTypeName) {
		JaxbPersistentClass jaxbClass= this.getPersistentClass(fullyQualifiedTypeName);
		return jaxbClass != null ? jaxbClass : this.getTransientClass(fullyQualifiedTypeName);
	}

	// ********** persistent enums **********
	
	public Iterable<JaxbPersistentEnum> getPersistentEnums() {
		return new SubIterableWrapper<JaxbType, JaxbPersistentEnum>(
				new FilteringIterable<JaxbType>(getTypes()) {
					@Override
					protected boolean accept(JaxbType o) {
						return o.getKind() == JaxbType.Kind.PERSISTENT_ENUM;
					}
				});
	}
	
	protected JaxbPersistentEnum buildPersistentEnum(JavaResourceEnum resourceEnum) {
		return this.getFactory().buildJavaPersistentEnum(this, resourceEnum);
	}
	
	public Iterable<JaxbPersistentEnum> getPersistentEnums(final JaxbPackage jaxbPackage) {
		return new FilteringIterable<JaxbPersistentEnum>(getPersistentEnums()) {
			@Override
			protected boolean accept(JaxbPersistentEnum o) {
				return o.getPackageName().equals(jaxbPackage.getName());
			}
		};
	}

	public JaxbPersistentEnum getPersistentEnum(String enumName) {
		for (JaxbPersistentEnum jaxbEnum : this.getPersistentEnums()) {
			if (StringTools.stringsAreEqual(jaxbEnum.getFullyQualifiedName(), enumName)) {
				return jaxbEnum;
			}
		}
		return null;
	}

	@Override
    public void stateChanged() {
		super.stateChanged();
		// forward to JAXB project
		this.jaxbProject.stateChanged();
    }
	
	
	// **************** validation ********************************************
	
	public void validate(List<IMessage> messages, IReporter reporter) {
		for (JaxbPackage pkg : this.packages.values()) {
			pkg.validate(messages, reporter);
		}
		for (JaxbType type : this.types.values()) {
			type.validate(messages, reporter);
		}
	}
}
