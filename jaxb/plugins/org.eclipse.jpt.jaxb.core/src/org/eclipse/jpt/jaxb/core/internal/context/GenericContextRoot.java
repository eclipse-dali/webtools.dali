/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.core.context.JaxbType.Kind;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.AbstractJavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * the context model root
 */
public class GenericContextRoot
		extends AbstractJaxbContextNode
		implements JaxbContextRoot {
	
	/* This object has no parent, so it must point to the JAXB project explicitly. */
	protected final JaxbProject jaxbProject;
	
	/* The map of package name to JaxbPackage objects */
	protected final Map<String, JaxbPackage> packages;
	
	/* The map of class name to JaxbType objects */
	protected final Map<String, JaxbType> types;
	
	
	public GenericContextRoot(JaxbProject jaxbProject) {
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
		// determine set of registries
		// (registries can be determined purely by resource model)
		final Set<JavaResourceType> registries = calculateRegistries();
		
		// determine initial set of persistent classes
		// (persistent classes that can be determined purely by resource model)
		final Set<JavaResourceType> initialPersistentClasses = calculateInitialPersistentClasses();
		
		final Set<JavaResourceEnum> initialPersistentEnums = calculateInitialPersistentEnums();
		
		final Set<AbstractJavaResourceType> initialTypes = new HashSet<AbstractJavaResourceType>(registries);
		initialTypes.addAll(initialPersistentClasses);
		initialTypes.addAll(initialPersistentEnums);
		
		// determine initial set of packages
		final Set<String> initialPackages = calculateInitialPackageNames(initialTypes);
		
		for (String pkg : initialPackages) {
			this.packages.put(pkg, buildPackage(pkg));
		}
		
		for (JavaResourceType resourceType : registries) {
			this.types.put(resourceType.getName(), buildRegistry(resourceType));
		}
		
		for (JavaResourceType resourceType : initialPersistentClasses) {
			this.types.put(resourceType.getName(), buildPersistentClass(resourceType));
		}
		
		for (JavaResourceEnum resourceType : initialPersistentEnums) {
			this.types.put(resourceType.getName(), buildPersistentEnum(resourceType));
		}
	}
	
	public void synchronizeWithResourceModel() {
		for (JaxbPackage each : getPackages()) {
			each.synchronizeWithResourceModel();
		}
		for (JaxbType each : getTypes()) {
			each.synchronizeWithResourceModel();
		}
	}
	
	public void update() {
		// determine set of registries
		// (registries can be determined purely by resource model)
		final Set<JavaResourceType> registries = calculateRegistries();
		
		// determine initial set of persistent classes
		// (persistent classes that can be determined purely by resource model)
		final Set<JavaResourceType> initialPersistentClasses = calculateInitialPersistentClasses();
		final Set<JavaResourceEnum> initialPersistentEnums = calculateInitialPersistentEnums();
		
		final Set<AbstractJavaResourceType> initialTypes = new HashSet<AbstractJavaResourceType>(registries);
		initialTypes.addAll(initialPersistentClasses);
		initialTypes.addAll(initialPersistentEnums);
		
		// determine initial set of packages
		final Set<String> initialPackages = calculateInitialPackageNames(initialTypes);
		
		final Set<String> packagesToUpdate = CollectionTools.<String>set();
		final Set<String> packagesToRemove = CollectionTools.set(this.packages.keySet());
		final Set<String> typesToUpdate = CollectionTools.<String>set();
		final Set<String> typesToRemove = CollectionTools.set(this.types.keySet());
		
		for (String pkg : initialPackages) {
			if (this.packages.containsKey(pkg)) {
				packagesToUpdate.add(pkg);
				packagesToRemove.remove(pkg);
			}
			else {
				this.addPackage(this.buildPackage(pkg));
			}
		}
		
		for (JavaResourceType resourceType : registries) {
			String className = resourceType.getQualifiedName();
			typesToRemove.remove(className);
			if (this.types.containsKey(className)) {
				if (this.types.get(className).getKind() == Kind.REGISTRY) {
					typesToUpdate.add(className);
				}
				else {
					this.removeType(className); // this will remove a type of another kind
					this.addType(buildRegistry(resourceType));
				}
			}
			else {
				this.addType(buildRegistry(resourceType));
			}
		}
		
		for (JavaResourceType resourceType : initialPersistentClasses) {
			String className = resourceType.getQualifiedName();
			typesToRemove.remove(className);
			if (this.types.containsKey(className)) {
				if (this.types.get(className).getKind() == Kind.PERSISTENT_CLASS) {
					typesToUpdate.add(className);
				}
				else {
					this.removeType(className); // this will remove a type of another kind
					this.addType(buildPersistentClass(resourceType));
				}
			}
			else {
				this.addType(buildPersistentClass(resourceType));
			}
		}
		
		for (JavaResourceEnum resourceEnum : initialPersistentEnums) {
			String className = resourceEnum.getQualifiedName();
			typesToRemove.remove(className);
			if (this.types.containsKey(className)) {
				if (this.types.get(className).getKind() == Kind.PERSISTENT_ENUM) {
					typesToUpdate.add(className);
				}
				else {
					this.removeType(className); // this will remove a type of another kind
					this.addType(buildPersistentEnum(resourceEnum));
				}
			}
			else {
				this.addType(buildPersistentEnum(resourceEnum));
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
	
	/*
	 * calculate set of packages that can be determined purely by resource model and the given
	 * set of classes.
	 * This should include:
	 *  - any annotated package 
	 *  - any package containing an included class
	 */
	protected Set<String> calculateInitialPackageNames(final Set<AbstractJavaResourceType> initialClasses) {
		final Set<String> packages = CollectionTools.set(
				new TransformationIterable<JavaResourcePackage, String>(
						getJaxbProject().getAnnotatedJavaResourcePackages()) {
					@Override
					protected String transform(JavaResourcePackage o) {
						return o.getName();
					}
				});
		for (AbstractJavaResourceType clazz : initialClasses) {
			packages.add(clazz.getPackageName());
		}
		return packages;
	}
	
	/*
	 * Calculate set of persistent classes that can be determined purely by resource model
	 * (so far, this should be all resource types with the @XmlType annotation)
	 * If both @XmlType and @XmlRegistry exist on a class, we will let @XmlRegistry take precedence
	 */
	protected Set<JavaResourceType> calculateInitialPersistentClasses() {
		return CollectionTools.set(
				new FilteringIterable<JavaResourceType>(
						getJaxbProject().getJavaSourceResourceTypes()) {
					@Override
					protected boolean accept(JavaResourceType o) {
						return o.getAnnotation(JAXB.XML_TYPE) != null && o.getAnnotation(JAXB.XML_REGISTRY) == null && o.getAnnotation(JAXB.XML_ENUM) == null;
					}
				});
	}
	
	/*
	 * Calculate set of persistent enums that can be determined purely by resource model
	 * (so far, this should be all resource types with the @XmlEnum annotation)
	 * If both @XmlEnum and @XmlRegistry exist on a class, we will let @XmlRegistry take precedence
	 */
	protected Set<JavaResourceEnum> calculateInitialPersistentEnums() {
		return CollectionTools.set(
				new FilteringIterable<JavaResourceEnum>(
						getJaxbProject().getJavaSourceResourceEnums()) {
					@Override
					protected boolean accept(JavaResourceEnum o) {
						return ((o.getAnnotation(JAXB.XML_ENUM) != null) || (o.getAnnotation(JAXB.XML_TYPE) != null)) && o.getAnnotation(JAXB.XML_REGISTRY) == null;
					}
				});
	}
	
	/*
	 * Calculate set of registries
	 * (this should be all resource types with the @XmlRegistry annotation)
	 */
	protected Set<JavaResourceType> calculateRegistries() {
		return CollectionTools.set(
				new FilteringIterable<JavaResourceType>(
						getJaxbProject().getJavaSourceResourceTypes()) {
					@Override
					protected boolean accept(JavaResourceType o) {
						return o.getAnnotation(JAXB.XML_REGISTRY) != null;
					}
				});
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
	
	protected void addType(JaxbType type) {
		if (this.types.containsKey(type.getFullyQualifiedName())) {
			throw new IllegalArgumentException("Type with that name already exists."); //$NON-NLS-1$
		}
		this.types.put(type.getFullyQualifiedName(), type);
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
						return o.getKind() == Kind.REGISTRY;
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
	
	
	// ********** persistent classes **********
	
	public Iterable<JaxbPersistentClass> getPersistentClasses() {
		return new SubIterableWrapper<JaxbType, JaxbPersistentClass>(
				new FilteringIterable<JaxbType>(getTypes()) {
					@Override
					protected boolean accept(JaxbType o) {
						return o.getKind() == Kind.PERSISTENT_CLASS;
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
	
	// ********** persistent enums **********
	
	public Iterable<JaxbPersistentEnum> getPersistentEnums() {
		return new SubIterableWrapper<JaxbType, JaxbPersistentEnum>(
				new FilteringIterable<JaxbType>(getTypes()) {
					@Override
					protected boolean accept(JaxbType o) {
						return o.getKind() == Kind.PERSISTENT_ENUM;
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
	}
}
