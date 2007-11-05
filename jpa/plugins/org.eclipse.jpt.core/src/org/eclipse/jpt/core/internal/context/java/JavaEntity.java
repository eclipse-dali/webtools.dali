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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.utility.internal.Filter;


public class JavaEntity extends JavaTypeMapping implements IJavaEntity
{
	protected Entity entityResource;
	
	protected String specifiedName;

	protected String defaultName;

	protected final ITable table;
//
//	protected List<ISecondaryTable> specifiedSecondaryTables;
//
//	protected List<IPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
//
//	protected List<IPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;
//
//	protected static final InheritanceType INHERITANCE_STRATEGY_EDEFAULT = InheritanceType.DEFAULT;
//
//	protected InheritanceType inheritanceStrategy = INHERITANCE_STRATEGY_EDEFAULT;
//
//	protected static final String DEFAULT_DISCRIMINATOR_VALUE_EDEFAULT = null;
//
//	protected String defaultDiscriminatorValue = DEFAULT_DISCRIMINATOR_VALUE_EDEFAULT;
//
//	protected static final String SPECIFIED_DISCRIMINATOR_VALUE_EDEFAULT = null;
//
//	protected String specifiedDiscriminatorValue = SPECIFIED_DISCRIMINATOR_VALUE_EDEFAULT;
//
//	protected static final String DISCRIMINATOR_VALUE_EDEFAULT = null;
//	
//	protected IDiscriminatorColumn discriminatorColumn;
//
//	protected ISequenceGenerator sequenceGenerator;
//
//	protected ITableGenerator tableGenerator;
//
//	protected List<IAttributeOverride> specifiedAttributeOverrides;
//
//	protected List<IAttributeOverride> defaultAttributeOverrides;
//
//	protected List<IAssociationOverride> specifiedAssociationOverrides;
//
//	protected List<IAssociationOverride> defaultAssociationOverrides;
//
//	protected List<INamedQuery> namedQueries;
//
//	protected List<INamedNativeQuery> namedNativeQueries;
//
//	protected String idClass;

	
	public JavaEntity(IJavaPersistentType parent) {
		super(parent);
		this.table = jpaFactory().createJavaTable(this);
//		this.discriminatorColumn = JpaJavaMappingsFactory.eINSTANCE.createJavaDiscriminatorColumn(new IDiscriminatorColumn.Owner(this), type, JavaDiscriminatorColumn.DECLARATION_ANNOTATION_ADAPTER);
//		this.getDefaultPrimaryKeyJoinColumns().add(this.createPrimaryKeyJoinColumn(0));
	}

	@Override
	public void initialize(JavaPersistentTypeResource persistentTypeResource) {
		super.initialize(persistentTypeResource);
		this.entityResource = (Entity) persistentTypeResource.mappingAnnotation(Entity.ANNOTATION_NAME);
		
		this.specifiedName = this.specifiedName(this.entityResource);
		this.defaultName = this.defaultName(persistentTypeResource);
		this.table.initialize(persistentTypeResource);
	}
	
//	private ITable.Owner buildTableOwner() {
//		return new ITable.Owner() {
//			public ITextRange validationTextRange() {
//				return JavaEntity.this.validationTextRange();
//			}
//
//			public ITypeMapping getTypeMapping() {
//				return JavaEntity.this;
//			}
//		};
//	}

	public String annotationName() {
		return Entity.ANNOTATION_NAME;
	}
	
	public String getKey() {
		return IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public boolean isMapped() {
		return true;
	}
	
	@Override
	public String getName() {
		return (this.getSpecifiedName() == null) ? this.getDefaultName() : this.getSpecifiedName();
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		this.entityResource.setName(newSpecifiedName);
		firePropertyChanged(IEntity.SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}

	public String getDefaultName() {
		return this.defaultName;
	}
	
	protected/*private-protected*/ void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		firePropertyChanged(IEntity.DEFAULT_NAME_PROPERTY, oldDefaultName, newDefaultName);
	}

	public ITable getTable() {
		return this.table;
	}

//	public EList<ISecondaryTable> getSpecifiedSecondaryTables() {
//		if (specifiedSecondaryTables == null) {
//			specifiedSecondaryTables = new EObjectContainmentEList<ISecondaryTable>(ISecondaryTable.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_SECONDARY_TABLES);
//		}
//		return specifiedSecondaryTables;
//	}
//
//	public EList<ISecondaryTable> getSecondaryTables() {
//		return getSpecifiedSecondaryTables();
//	}
//
//	public boolean containsSecondaryTable(String name) {
//		return containsSecondaryTable(name, getSecondaryTables());
//	}
//
//	public boolean containsSpecifiedSecondaryTable(String name) {
//		return containsSecondaryTable(name, getSpecifiedSecondaryTables());
//	}
//
//	private boolean containsSecondaryTable(String name, List<ISecondaryTable> secondaryTables) {
//		for (ISecondaryTable secondaryTable : secondaryTables) {
//			String secondaryTableName = secondaryTable.getName();
//			if (secondaryTableName != null && secondaryTableName.equals(name)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public InheritanceType getInheritanceStrategy() {
//		return inheritanceStrategy;
//	}
//
//	public void setInheritanceStrategy(InheritanceType newInheritanceStrategy) {
//		InheritanceType oldInheritanceStrategy = inheritanceStrategy;
//		inheritanceStrategy = newInheritanceStrategy == null ? INHERITANCE_STRATEGY_EDEFAULT : newInheritanceStrategy;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__INHERITANCE_STRATEGY, oldInheritanceStrategy, inheritanceStrategy));
//	}
//
//	public IDiscriminatorColumn getDiscriminatorColumn() {
//		return discriminatorColumn;
//	}
//
//	public NotificationChain basicSetDiscriminatorColumn(IDiscriminatorColumn newDiscriminatorColumn, NotificationChain msgs) {
//		IDiscriminatorColumn oldDiscriminatorColumn = discriminatorColumn;
//		discriminatorColumn = newDiscriminatorColumn;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__DISCRIMINATOR_COLUMN, oldDiscriminatorColumn, newDiscriminatorColumn);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//
//	public ISequenceGenerator getSequenceGenerator() {
//		return sequenceGenerator;
//	}
//
//	public NotificationChain basicSetSequenceGenerator(ISequenceGenerator newSequenceGenerator, NotificationChain msgs) {
//		ISequenceGenerator oldSequenceGenerator = sequenceGenerator;
//		sequenceGenerator = newSequenceGenerator;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR, oldSequenceGenerator, newSequenceGenerator);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//	public void setSequenceGenerator(ISequenceGenerator newSequenceGenerator) {
//		if (newSequenceGenerator != sequenceGenerator) {
//			NotificationChain msgs = null;
//			if (sequenceGenerator != null)
//				msgs = ((InternalEObject) sequenceGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR, null, msgs);
//			if (newSequenceGenerator != null)
//				msgs = ((InternalEObject) newSequenceGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR, null, msgs);
//			msgs = basicSetSequenceGenerator(newSequenceGenerator, msgs);
//			if (msgs != null)
//				msgs.dispatch();
//		}
//		else if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR, newSequenceGenerator, newSequenceGenerator));
//	}
//
//	public ITableGenerator getTableGenerator() {
//		return tableGenerator;
//	}
//
//	public NotificationChain basicSetTableGenerator(ITableGenerator newTableGenerator, NotificationChain msgs) {
//		ITableGenerator oldTableGenerator = tableGenerator;
//		tableGenerator = newTableGenerator;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR, oldTableGenerator, newTableGenerator);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//	public void setTableGenerator(ITableGenerator newTableGenerator) {
//		if (newTableGenerator != tableGenerator) {
//			NotificationChain msgs = null;
//			if (tableGenerator != null)
//				msgs = ((InternalEObject) tableGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR, null, msgs);
//			if (newTableGenerator != null)
//				msgs = ((InternalEObject) newTableGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR, null, msgs);
//			msgs = basicSetTableGenerator(newTableGenerator, msgs);
//			if (msgs != null)
//				msgs.dispatch();
//		}
//		else if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR, newTableGenerator, newTableGenerator));
//	}
//
//	public String getDefaultDiscriminatorValue() {
//		return defaultDiscriminatorValue;
//	}
//
//	public void setDefaultDiscriminatorValue(String newDefaultDiscriminatorValue) {
//		String oldDefaultDiscriminatorValue = defaultDiscriminatorValue;
//		defaultDiscriminatorValue = newDefaultDiscriminatorValue;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_DISCRIMINATOR_VALUE, oldDefaultDiscriminatorValue, defaultDiscriminatorValue));
//	}
//
//	public String getSpecifiedDiscriminatorValue() {
//		return specifiedDiscriminatorValue;
//	}
//
//	public void setSpecifiedDiscriminatorValue(String newSpecifiedDiscriminatorValue) {
//		String oldSpecifiedDiscriminatorValue = specifiedDiscriminatorValue;
//		specifiedDiscriminatorValue = newSpecifiedDiscriminatorValue;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_DISCRIMINATOR_VALUE, oldSpecifiedDiscriminatorValue, specifiedDiscriminatorValue));
//	}
//
//	public String getDiscriminatorValue() {
//		return (this.getSpecifiedDiscriminatorValue() == null) ? getDefaultDiscriminatorValue() : this.getSpecifiedDiscriminatorValue();
//	}
//
//	public EList<IPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
//		return this.getSpecifiedPrimaryKeyJoinColumns().isEmpty() ? this.getDefaultPrimaryKeyJoinColumns() : this.getSpecifiedPrimaryKeyJoinColumns();
//	}
//
//	public EList<IPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
//		if (specifiedPrimaryKeyJoinColumns == null) {
//			specifiedPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS);
//		}
//		return specifiedPrimaryKeyJoinColumns;
//	}
//
//	public EList<IPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
//		if (defaultPrimaryKeyJoinColumns == null) {
//			defaultPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS);
//		}
//		return defaultPrimaryKeyJoinColumns;
//	}
//
//	public EList<IAttributeOverride> getAttributeOverrides() {
//		EList<IAttributeOverride> list = new EObjectEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__ATTRIBUTE_OVERRIDES);
//		list.addAll(getSpecifiedAttributeOverrides());
//		list.addAll(getDefaultAttributeOverrides());
//		return list;
//	}
//
//	public IAttributeOverride attributeOverrideNamed(String name) {
//		return (IAttributeOverride) overrideNamed(name, getAttributeOverrides());
//	}
//
//	public boolean containsAttributeOverride(String name) {
//		return containsOverride(name, getAttributeOverrides());
//	}
//
//	public boolean containsDefaultAttributeOverride(String name) {
//		return containsOverride(name, getDefaultAttributeOverrides());
//	}
//
//	public boolean containsSpecifiedAttributeOverride(String name) {
//		return containsOverride(name, getSpecifiedAttributeOverrides());
//	}
//
//	public boolean containsAssociationOverride(String name) {
//		return containsOverride(name, getAssociationOverrides());
//	}
//
//	public boolean containsSpecifiedAssociationOverride(String name) {
//		return containsOverride(name, getSpecifiedAssociationOverrides());
//	}
//
//	public boolean containsDefaultAssociationOverride(String name) {
//		return containsOverride(name, getDefaultAssociationOverrides());
//	}
//
//	private IOverride overrideNamed(String name, List<? extends IOverride> overrides) {
//		for (IOverride override : overrides) {
//			String overrideName = override.getName();
//			if (overrideName == null && name == null) {
//				return override;
//			}
//			if (overrideName != null && overrideName.equals(name)) {
//				return override;
//			}
//		}
//		return null;
//	}
//
//	private boolean containsOverride(String name, List<? extends IOverride> overrides) {
//		return overrideNamed(name, overrides) != null;
//	}
//
//	public EList<IAttributeOverride> getSpecifiedAttributeOverrides() {
//		if (specifiedAttributeOverrides == null) {
//			specifiedAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES);
//		}
//		return specifiedAttributeOverrides;
//	}
//	
//	public EList<IAttributeOverride> getDefaultAttributeOverrides() {
//		if (defaultAttributeOverrides == null) {
//			defaultAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ATTRIBUTE_OVERRIDES);
//		}
//		return defaultAttributeOverrides;
//	}
//
//	public EList<IAssociationOverride> getAssociationOverrides() {
//		EList<IAssociationOverride> list = new EObjectEList<IAssociationOverride>(IAssociationOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__ASSOCIATION_OVERRIDES);
//		list.addAll(getSpecifiedAssociationOverrides());
//		list.addAll(getDefaultAssociationOverrides());
//		return list;
//	}
//
//	public EList<IAssociationOverride> getSpecifiedAssociationOverrides() {
//		if (specifiedAssociationOverrides == null) {
//			specifiedAssociationOverrides = new EObjectContainmentEList<IAssociationOverride>(IAssociationOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ASSOCIATION_OVERRIDES);
//		}
//		return specifiedAssociationOverrides;
//	}
//
//	public EList<IAssociationOverride> getDefaultAssociationOverrides() {
//		if (defaultAssociationOverrides == null) {
//			defaultAssociationOverrides = new EObjectContainmentEList<IAssociationOverride>(IAssociationOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ASSOCIATION_OVERRIDES);
//		}
//		return defaultAssociationOverrides;
//	}
//
//	public EList<INamedQuery> getNamedQueries() {
//		if (namedQueries == null) {
//			namedQueries = new EObjectContainmentEList<INamedQuery>(INamedQuery.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_QUERIES);
//		}
//		return namedQueries;
//	}
//
//	public EList<INamedNativeQuery> getNamedNativeQueries() {
//		if (namedNativeQueries == null) {
//			namedNativeQueries = new EObjectContainmentEList<INamedNativeQuery>(INamedNativeQuery.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_NATIVE_QUERIES);
//		}
//		return namedNativeQueries;
//	}
//
//	public String getIdClass() {
//		return idClass;
//	}
//
//	public void setIdClass(String newIdClass) {
//		String oldIdClass = idClass;
//		idClass = newIdClass;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__ID_CLASS, oldIdClass, idClass));
//	}
//
//	public boolean discriminatorValueIsAllowed() {
//		return !getType().isAbstract();
//	}
//
//	public IEntity parentEntity() {
//		for (Iterator<IPersistentType> i = getPersistentType().inheritanceHierarchy(); i.hasNext();) {
//			ITypeMapping typeMapping = i.next().getMapping();
//			if (typeMapping != this && typeMapping instanceof IEntity) {
//				return (IEntity) typeMapping;
//			}
//		}
//		return this;
//	}

	public IEntity rootEntity() {
		IEntity rootEntity = null;
		for (Iterator<IPersistentType> i = getPersistentType().inheritanceHierarchy(); i.hasNext();) {
			IPersistentType persistentType = i.next();
			if (persistentType.getMapping() instanceof IEntity) {
				rootEntity = (IEntity) persistentType.getMapping();
			}
		}
		return rootEntity;
	}

	@Override
	public String getTableName() {
		return getTable().getName();
	}
//
//	@Override
//	public Table primaryDbTable() {
//		return getTable().dbTable();
//	}
//
//	@Override
//	public Table dbTable(String tableName) {
//		for (Iterator<ITable> stream = this.associatedTablesIncludingInherited(); stream.hasNext();) {
//			Table dbTable = stream.next().dbTable();
//			if (dbTable != null && dbTable.matchesShortJavaClassName(tableName)) {
//				return dbTable;
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public Schema dbSchema() {
//		return getTable().dbSchema();
//	}

	@Override
	public void update(JavaPersistentTypeResource persistentTypeResource) {
		super.update(persistentTypeResource);
		this.entityResource = (Entity) persistentTypeResource.mappingAnnotation(Entity.ANNOTATION_NAME);
		
		this.setSpecifiedName(this.specifiedName(this.entityResource));
		this.setDefaultName(this.defaultName(persistentTypeResource));
		
		updateTable(persistentTypeResource);
	}
	
	protected void updateTable(JavaPersistentTypeResource persistentTypeResource) {
		getTable().update(persistentTypeResource);
	}
	
	protected String specifiedName(Entity entityResource) {
		return entityResource.getName();
	}
	
	protected String defaultName(JavaPersistentTypeResource persistentTypeResource) {
		return persistentTypeResource.getName();
	}
	
//	@Override
//	public void updateFromJava(CompilationUnit astRoot) {
//		this.setSpecifiedName(this.getType().annotationElementValue(NAME_ADAPTER, astRoot));
//		this.setDefaultName(this.getType().getName());
//		this.getJavaTable().updateFromJava(astRoot);
//		this.updateSecondaryTablesFromJava(astRoot);
//		this.updateNamedQueriesFromJava(astRoot);
//		this.updateNamedNativeQueriesFromJava(astRoot);
//		this.updateSpecifiedPrimaryKeyJoinColumnsFromJava(astRoot);
//		this.updateAttributeOverridesFromJava(astRoot);
//		this.updateAssociationOverridesFromJava(astRoot);
//		this.setInheritanceStrategy(InheritanceType.fromJavaAnnotationValue(this.inheritanceStrategyAdapter.getValue(astRoot)));
//		this.getJavaDiscriminatorColumn().updateFromJava(astRoot);
//		this.setSpecifiedDiscriminatorValue(this.discriminatorValueAdapter.getValue(astRoot));
//		this.setDefaultDiscriminatorValue(this.javaDefaultDiscriminatorValue());
//		this.updateTableGeneratorFromJava(astRoot);
//		this.updateSequenceGeneratorFromJava(astRoot);
//		this.updateIdClassFromJava(astRoot);
//	}
//
//	private void updateIdClassFromJava(CompilationUnit astRoot) {
//		if (this.idClassAnnotationAdapter.getAnnotation(astRoot) == null) {
//			this.setIdClass(null);
//		}
//		else {
//			this.setIdClass(this.idClassValueAdapter.getValue(astRoot));
//		}
//	}
//
//	private JavaTable getJavaTable() {
//		return (JavaTable) this.table;
//	}
//
//	private JavaDiscriminatorColumn getJavaDiscriminatorColumn() {
//		return (JavaDiscriminatorColumn) this.discriminatorColumn;
//	}
//
//	private void updateTableGeneratorFromJava(CompilationUnit astRoot) {
//		if (this.tableGeneratorAnnotationAdapter.getAnnotation(astRoot) == null) {
//			if (this.tableGenerator != null) {
//				setTableGenerator(null);
//			}
//		}
//		else {
//			if (this.tableGenerator == null) {
//				setTableGenerator(createTableGenerator());
//			}
//			this.getJavaTableGenerator().updateFromJava(astRoot);
//		}
//	}
//
//	private JavaTableGenerator getJavaTableGenerator() {
//		return (JavaTableGenerator) this.tableGenerator;
//	}
//
//	private void updateSequenceGeneratorFromJava(CompilationUnit astRoot) {
//		if (this.sequenceGeneratorAnnotationAdapter.getAnnotation(astRoot) == null) {
//			if (this.sequenceGenerator != null) {
//				setSequenceGenerator(null);
//			}
//		}
//		else {
//			if (this.sequenceGenerator == null) {
//				setSequenceGenerator(createSequenceGenerator());
//			}
//			this.getJavaSequenceGenerator().updateFromJava(astRoot);
//		}
//	}
//
//	private JavaSequenceGenerator getJavaSequenceGenerator() {
//		return (JavaSequenceGenerator) this.sequenceGenerator;
//	}
//
//	/**
//	 * From the Spec:
//	 * If the DiscriminatorValue annotation is not specified, a
//	 * provider-specific function to generate a value representing
//	 * the entity type is used for the value of the discriminator
//	 * column. If the DiscriminatorType is STRING, the discriminator
//	 * value default is the entity name.
//	 * 
//	 * TODO extension point for provider-specific function?
//	 */
//	private String javaDefaultDiscriminatorValue() {
//		if (this.getType().isAbstract()) {
//			return null;
//		}
//		if (!this.discriminatorType().isString()) {
//			return null;
//		}
//		return this.getName();
//	}
//
//	private DiscriminatorType discriminatorType() {
//		return this.getDiscriminatorColumn().getDiscriminatorType();
//	}
//
//	/**
//	 * here we just worry about getting the attribute override lists the same size;
//	 * then we delegate to the attribute overrides to synch themselves up
//	 */
//	private void updateAttributeOverridesFromJava(CompilationUnit astRoot) {
//		// synchronize the model attribute overrides with the Java source
//		List<IAttributeOverride> attributeOverrides = getSpecifiedAttributeOverrides();
//		int persSize = attributeOverrides.size();
//		int javaSize = 0;
//		boolean allJavaAnnotationsFound = false;
//		for (int i = 0; i < persSize; i++) {
//			JavaAttributeOverride attributeOverride = (JavaAttributeOverride) attributeOverrides.get(i);
//			if (attributeOverride.annotation(astRoot) == null) {
//				allJavaAnnotationsFound = true;
//				break; // no need to go any further
//			}
//			attributeOverride.updateFromJava(astRoot);
//			javaSize++;
//		}
//		if (allJavaAnnotationsFound) {
//			// remove any model attribute overrides beyond those that correspond to the Java annotations
//			while (persSize > javaSize) {
//				persSize--;
//				attributeOverrides.remove(persSize);
//			}
//		}
//		else {
//			// add new model attribute overrides until they match the Java annotations
//			while (!allJavaAnnotationsFound) {
//				JavaAttributeOverride attributeOverride = this.createJavaAttributeOverride(javaSize);
//				if (attributeOverride.annotation(astRoot) == null) {
//					allJavaAnnotationsFound = true;
//				}
//				else {
//					getSpecifiedAttributeOverrides().add(attributeOverride);
//					attributeOverride.updateFromJava(astRoot);
//					javaSize++;
//				}
//			}
//		}
//	}
//
//	/**
//	 * here we just worry about getting the attribute override lists the same size;
//	 * then we delegate to the attribute overrides to synch themselves up
//	 */
//	private void updateAssociationOverridesFromJava(CompilationUnit astRoot) {
//		// synchronize the model attribute overrides with the Java source
//		List<IAssociationOverride> associationOverrides = getSpecifiedAssociationOverrides();
//		int persSize = associationOverrides.size();
//		int javaSize = 0;
//		boolean allJavaAnnotationsFound = false;
//		for (int i = 0; i < persSize; i++) {
//			JavaAssociationOverride associationOverride = (JavaAssociationOverride) associationOverrides.get(i);
//			if (associationOverride.annotation(astRoot) == null) {
//				allJavaAnnotationsFound = true;
//				break; // no need to go any further
//			}
//			associationOverride.updateFromJava(astRoot);
//			javaSize++;
//		}
//		if (allJavaAnnotationsFound) {
//			// remove any model attribute overrides beyond those that correspond to the Java annotations
//			while (persSize > javaSize) {
//				persSize--;
//				associationOverrides.remove(persSize);
//			}
//		}
//		else {
//			// add new model attribute overrides until they match the Java annotations
//			while (!allJavaAnnotationsFound) {
//				JavaAssociationOverride associationOverride = this.createJavaAssociationOverride(javaSize);
//				if (associationOverride.annotation(astRoot) == null) {
//					allJavaAnnotationsFound = true;
//				}
//				else {
//					getSpecifiedAssociationOverrides().add(associationOverride);
//					associationOverride.updateFromJava(astRoot);
//					javaSize++;
//				}
//			}
//		}
//	}
//
//	/**
//	 * here we just worry about getting the secondary table lists the same size;
//	 * then we delegate to the secondary tables to synch themselves up
//	 */
//	private void updateSecondaryTablesFromJava(CompilationUnit astRoot) {
//		// synchronize the model secondary tables with the Java source
//		List<ISecondaryTable> sTables = this.getSecondaryTables();
//		int persSize = sTables.size();
//		int javaSize = 0;
//		boolean allJavaAnnotationsFound = false;
//		for (int i = 0; i < persSize; i++) {
//			JavaSecondaryTable secondaryTable = (JavaSecondaryTable) sTables.get(i);
//			if (secondaryTable.annotation(astRoot) == null) {
//				allJavaAnnotationsFound = true;
//				break; // no need to go any further
//			}
//			secondaryTable.updateFromJava(astRoot);
//			javaSize++;
//		}
//		if (allJavaAnnotationsFound) {
//			// remove any model secondary tables beyond those that correspond to the Java annotations
//			while (persSize > javaSize) {
//				persSize--;
//				sTables.remove(persSize);
//			}
//		}
//		else {
//			// add new model join columns until they match the Java annotations
//			while (!allJavaAnnotationsFound) {
//				JavaSecondaryTable secondaryTable = this.createJavaSecondaryTable(javaSize);
//				if (secondaryTable.annotation(astRoot) == null) {
//					allJavaAnnotationsFound = true;
//				}
//				else {
//					getSecondaryTables().add(secondaryTable);
//					secondaryTable.updateFromJava(astRoot);
//					javaSize++;
//				}
//			}
//		}
//	}
//
//	/**
//	 * here we just worry about getting the named query lists the same size;
//	 * then we delegate to the named queries to synch themselves up
//	 */
//	private void updateNamedQueriesFromJava(CompilationUnit astRoot) {
//		// synchronize the model named queries with the Java source
//		List<INamedQuery> queries = this.getNamedQueries();
//		int persSize = queries.size();
//		int javaSize = 0;
//		boolean allJavaAnnotationsFound = false;
//		for (int i = 0; i < persSize; i++) {
//			JavaNamedQuery namedQuery = (JavaNamedQuery) queries.get(i);
//			if (namedQuery.annotation(astRoot) == null) {
//				allJavaAnnotationsFound = true;
//				break; // no need to go any further
//			}
//			namedQuery.updateFromJava(astRoot);
//			javaSize++;
//		}
//		if (allJavaAnnotationsFound) {
//			// remove any model named queries beyond those that correspond to the Java annotations
//			while (persSize > javaSize) {
//				persSize--;
//				queries.remove(persSize);
//			}
//		}
//		else {
//			// add new model join columns until they match the Java annotations
//			while (!allJavaAnnotationsFound) {
//				JavaNamedQuery javaNamedQuery = this.createJavaNamedQuery(javaSize);
//				if (javaNamedQuery.annotation(astRoot) == null) {
//					allJavaAnnotationsFound = true;
//				}
//				else {
//					getNamedQueries().add(javaNamedQuery);
//					javaNamedQuery.updateFromJava(astRoot);
//					javaSize++;
//				}
//			}
//		}
//	}
//
//	/**
//	 * here we just worry about getting the named native query lists the same size;
//	 * then we delegate to the named native queries to synch themselves up
//	 */
//	private void updateNamedNativeQueriesFromJava(CompilationUnit astRoot) {
//		// synchronize the model named queries with the Java source
//		List<INamedNativeQuery> queries = this.getNamedNativeQueries();
//		int persSize = queries.size();
//		int javaSize = 0;
//		boolean allJavaAnnotationsFound = false;
//		for (int i = 0; i < persSize; i++) {
//			JavaNamedNativeQuery namedQuery = (JavaNamedNativeQuery) queries.get(i);
//			if (namedQuery.annotation(astRoot) == null) {
//				allJavaAnnotationsFound = true;
//				break; // no need to go any further
//			}
//			namedQuery.updateFromJava(astRoot);
//			javaSize++;
//		}
//		if (allJavaAnnotationsFound) {
//			// remove any model named queries beyond those that correspond to the Java annotations
//			while (persSize > javaSize) {
//				persSize--;
//				queries.remove(persSize);
//			}
//		}
//		else {
//			// add new model join columns until they match the Java annotations
//			while (!allJavaAnnotationsFound) {
//				JavaNamedNativeQuery javaNamedQuery = this.createJavaNamedNativeQuery(javaSize);
//				if (javaNamedQuery.annotation(astRoot) == null) {
//					allJavaAnnotationsFound = true;
//				}
//				else {
//					getNamedNativeQueries().add(javaNamedQuery);
//					javaNamedQuery.updateFromJava(astRoot);
//					javaSize++;
//				}
//			}
//		}
//	}
//
//	/**
//	 * here we just worry about getting the primary key join column lists
//	 * the same size; then we delegate to the join columns to synch
//	 * themselves up
//	 */
//	private void updateSpecifiedPrimaryKeyJoinColumnsFromJava(CompilationUnit astRoot) {
//		// synchronize the model primary key join columns with the Java source
//		List<IPrimaryKeyJoinColumn> pkJoinColumns = getSpecifiedPrimaryKeyJoinColumns();
//		int persSize = pkJoinColumns.size();
//		int javaSize = 0;
//		boolean allJavaAnnotationsFound = false;
//		for (int i = 0; i < persSize; i++) {
//			JavaPrimaryKeyJoinColumn pkJoinColumn = (JavaPrimaryKeyJoinColumn) pkJoinColumns.get(i);
//			if (pkJoinColumn.annotation(astRoot) == null) {
//				allJavaAnnotationsFound = true;
//				break; // no need to go any further
//			}
//			pkJoinColumn.updateFromJava(astRoot);
//			javaSize++;
//		}
//		if (allJavaAnnotationsFound) {
//			// remove any model primary key join columns beyond those that correspond to the Java annotations
//			while (persSize > javaSize) {
//				persSize--;
//				pkJoinColumns.remove(persSize);
//			}
//		}
//		else {
//			// add new model join columns until they match the Java annotations
//			while (!allJavaAnnotationsFound) {
//				JavaPrimaryKeyJoinColumn jpkjc = this.createJavaPrimaryKeyJoinColumn(javaSize);
//				if (jpkjc.annotation(astRoot) == null) {
//					allJavaAnnotationsFound = true;
//				}
//				else {
//					getSpecifiedPrimaryKeyJoinColumns().add(jpkjc);
//					jpkjc.updateFromJava(astRoot);
//					javaSize++;
//				}
//			}
//		}
//	}
//
//	public String primaryKeyColumnName() {
//		String pkColumnName = null;
//		for (Iterator<IPersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext();) {
//			IPersistentAttribute attribute = stream.next();
//			String name = attribute.primaryKeyColumnName();
//			if (pkColumnName == null) {
//				pkColumnName = name;
//			}
//			else if (name != null) {
//				// if we encounter a composite primary key, return null
//				return null;
//			}
//		}
//		// if we encounter only a single primary key column name, return it
//		return pkColumnName;
//	}
//
//	public String primaryKeyAttributeName() {
//		String pkColumnName = null;
//		String pkAttributeName = null;
//		for (Iterator<IPersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext();) {
//			IPersistentAttribute attribute = stream.next();
//			String name = attribute.primaryKeyColumnName();
//			if (pkColumnName == null) {
//				pkColumnName = name;
//				pkAttributeName = attribute.getName();
//			}
//			else if (name != null) {
//				// if we encounter a composite primary key, return null
//				return null;
//			}
//		}
//		// if we encounter only a single primary key column name, return it
//		return pkAttributeName;
//	}
//
//	@Override
//	public boolean tableNameIsInvalid(String tableName) {
//		return !CollectionTools.contains(this.associatedTableNamesIncludingInherited(), tableName);
//	}
//
//	@Override
//	public Iterator<ITable> associatedTables() {
//		return new CompositeIterator<ITable>(this.getTable(), this.getSecondaryTables().iterator());
//	}
//
//	@Override
//	public Iterator<ITable> associatedTablesIncludingInherited() {
//		return new CompositeIterator<ITable>(new TransformationIterator<ITypeMapping, Iterator<ITable>>(this.inheritanceHierarchy()) {
//			@Override
//			protected Iterator<ITable> transform(ITypeMapping mapping) {
//				return new FilteringIterator<ITable>(mapping.associatedTables()) {
//					@Override
//					protected boolean accept(Object o) {
//						return true;
//						//TODO
//						//filtering these out so as to avoid the duplicate table, root and children share the same table
//						//return !(o instanceof SingleTableInheritanceChildTableImpl);
//					}
//				};
//			}
//		});
//	}
//
//	@Override
//	public Iterator<String> associatedTableNamesIncludingInherited() {
//		return this.nonNullTableNames(this.associatedTablesIncludingInherited());
//	}
//
//	private Iterator<String> nonNullTableNames(Iterator<ITable> tables) {
//		return new FilteringIterator<String>(this.tableNames(tables)) {
//			@Override
//			protected boolean accept(Object o) {
//				return o != null;
//			}
//		};
//	}
//
//	private Iterator<String> tableNames(Iterator<ITable> tables) {
//		return new TransformationIterator<ITable, String>(tables) {
//			@Override
//			protected String transform(ITable t) {
//				return t.getName();
//			}
//		};
//	}
//
//	/**
//	 * Return an iterator of Entities, each which inherits from the one before,
//	 * and terminates at the root entity (or at the point of cyclicity).
//	 */
//	private Iterator<ITypeMapping> inheritanceHierarchy() {
//		return new TransformationIterator<IPersistentType, ITypeMapping>(getPersistentType().inheritanceHierarchy()) {
//			@Override
//			protected ITypeMapping transform(IPersistentType type) {
//				return type.getMapping();
//			}
//		};
//		//TODO once we support inheritance, which of these should we use??
//		//return this.getInheritance().typeMappingLineage();
//	}
//
//	public Iterator<String> allOverridableAttributeNames() {
//		return new CompositeIterator<String>(new TransformationIterator<ITypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
//			@Override
//			protected Iterator<String> transform(ITypeMapping mapping) {
//				return mapping.overridableAttributeNames();
//			}
//		});
//	}
//
//	public Iterator<String> allOverridableAssociationNames() {
//		return new CompositeIterator<String>(new TransformationIterator<ITypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
//			@Override
//			protected Iterator<String> transform(ITypeMapping mapping) {
//				return mapping.overridableAssociationNames();
//			}
//		});
//	}
//
//	public IAttributeOverride createAttributeOverride(int index) {
//		return createJavaAttributeOverride(index);
//	}
//
//	private JavaAttributeOverride createJavaAttributeOverride(int index) {
//		return JavaAttributeOverride.createAttributeOverride(new AttributeOverrideOwner(this), this.getType(), index);
//	}
//
//	public IAssociationOverride createAssociationOverride(int index) {
//		return createJavaAssociationOverride(index);
//	}
//
//	private JavaAssociationOverride createJavaAssociationOverride(int index) {
//		return JavaAssociationOverride.createAssociationOverride(new AssociationOverrideOwner(this), this.getType(), index);
//	}
//
//	public JavaSecondaryTable createSecondaryTable(int index) {
//		return createJavaSecondaryTable(index);
//	}
//
//	private JavaSecondaryTable createJavaSecondaryTable(int index) {
//		return JavaSecondaryTable.createJavaSecondaryTable(buildSecondaryTableOwner(), this.getType(), index);
//	}
//
//	private ITable.Owner buildSecondaryTableOwner() {
//		return new ITable.Owner() {
//			public ITextRange validationTextRange() {
//				return JavaEntity.this.validationTextRange();
//			}
//
//			public ITypeMapping getTypeMapping() {
//				return JavaEntity.this;
//			}
//		};
//	}
//
//	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
//		return !this.getSpecifiedPrimaryKeyJoinColumns().isEmpty();
//	}
//
//	public IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index) {
//		return this.createJavaPrimaryKeyJoinColumn(index);
//	}
//
//	private JavaPrimaryKeyJoinColumn createJavaPrimaryKeyJoinColumn(int index) {
//		return JavaPrimaryKeyJoinColumn.createEntityPrimaryKeyJoinColumn(buildPkJoinColumnOwner(), this.getType(), index);
//	}
//
//	protected IAbstractJoinColumn.Owner buildPkJoinColumnOwner() {
//		return new IEntity.PrimaryKeyJoinColumnOwner(this);
//	}
//
//	public JavaNamedQuery createNamedQuery(int index) {
//		return createJavaNamedQuery(index);
//	}
//
//	private JavaNamedQuery createJavaNamedQuery(int index) {
//		return JavaNamedQuery.createJavaNamedQuery(this.getType(), index);
//	}
//
//	public JavaNamedNativeQuery createNamedNativeQuery(int index) {
//		return createJavaNamedNativeQuery(index);
//	}
//
//	private JavaNamedNativeQuery createJavaNamedNativeQuery(int index) {
//		return JavaNamedNativeQuery.createJavaNamedNativeQuery(this.getType(), index);
//	}
//
//	public ISequenceGenerator createSequenceGenerator() {
//		return JpaJavaMappingsFactory.eINSTANCE.createJavaSequenceGenerator(getType());
//	}
//
//	public ITableGenerator createTableGenerator() {
//		return JpaJavaMappingsFactory.eINSTANCE.createJavaTableGenerator(getType());
//	}

	// ********** misc **********
//	private static void attributeChanged(Object value, AnnotationAdapter annotationAdapter) {
//		Annotation annotation = annotationAdapter.getAnnotation();
//		if (value == null) {
//			if (annotation != null) {
//				annotationAdapter.removeAnnotation();
//			}
//		}
//		else {
//			if (annotation == null) {
//				annotationAdapter.newMarkerAnnotation();
//			}
//		}
//	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
//		result = this.getJavaTable().candidateValuesFor(pos, filter, astRoot);
//		if (result != null) {
//			return result;
//		}
//		for (ISecondaryTable sTable : this.getSecondaryTables()) {
//			result = ((JavaSecondaryTable) sTable).candidateValuesFor(pos, filter, astRoot);
//			if (result != null) {
//				return result;
//			}
//		}
//		for (IPrimaryKeyJoinColumn column : this.getPrimaryKeyJoinColumns()) {
//			result = ((JavaPrimaryKeyJoinColumn) column).candidateValuesFor(pos, filter, astRoot);
//			if (result != null) {
//				return result;
//			}
//		}
//		for (IAttributeOverride override : this.getAttributeOverrides()) {
//			result = ((JavaAttributeOverride) override).candidateValuesFor(pos, filter, astRoot);
//			if (result != null) {
//				return result;
//			}
//		}
//		for (IAssociationOverride override : this.getAssociationOverrides()) {
//			result = ((JavaAssociationOverride) override).candidateValuesFor(pos, filter, astRoot);
//			if (result != null) {
//				return result;
//			}
//		}
//		result = this.getJavaDiscriminatorColumn().candidateValuesFor(pos, filter, astRoot);
//		if (result != null) {
//			return result;
//		}
//		JavaTableGenerator jtg = this.getJavaTableGenerator();
//		if (jtg != null) {
//			result = jtg.candidateValuesFor(pos, filter, astRoot);
//			if (result != null) {
//				return result;
//			}
//		}
//		JavaSequenceGenerator jsg = this.getJavaSequenceGenerator();
//		if (jsg != null) {
//			result = jsg.candidateValuesFor(pos, filter, astRoot);
//			if (result != null) {
//				return result;
//			}
//		}
		return null;
	}
}
