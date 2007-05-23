/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.mappings.DiscriminatorType;
import org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.INamedNativeQuery;
import org.eclipse.jpt.core.internal.mappings.INamedQuery;
import org.eclipse.jpt.core.internal.mappings.IOverride;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.jpt.core.internal.mappings.ISequenceGenerator;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.ITableGenerator;
import org.eclipse.jpt.core.internal.mappings.InheritanceType;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaEntity()
 * @model kind="class"
 * @generated
 */
public class JavaEntity extends JavaTypeMapping implements IEntity
{
	/**
	 * The default value of the '{@link #getSpecifiedName() <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedName() <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedName()
	 * @generated
	 * @ordered
	 */
	protected String specifiedName = SPECIFIED_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultName() <em>Default Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultName() <em>Default Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultName()
	 * @generated
	 * @ordered
	 */
	protected String defaultName = DEFAULT_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTable() <em>Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTable()
	 * @generated
	 * @ordered
	 */
	protected ITable table;

	/**
	 * The cached value of the '{@link #getSpecifiedSecondaryTables() <em>Specified Secondary Tables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSecondaryTables()
	 * @generated
	 * @ordered
	 */
	protected EList<ISecondaryTable> specifiedSecondaryTables;

	/**
	 * The cached value of the '{@link #getSpecifiedPrimaryKeyJoinColumns() <em>Specified Primary Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedPrimaryKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;

	/**
	 * The cached value of the '{@link #getDefaultPrimaryKeyJoinColumns() <em>Default Primary Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultPrimaryKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	/**
	 * The default value of the '{@link #getInheritanceStrategy() <em>Inheritance Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInheritanceStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final InheritanceType INHERITANCE_STRATEGY_EDEFAULT = InheritanceType.DEFAULT;

	/**
	 * The cached value of the '{@link #getInheritanceStrategy() <em>Inheritance Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInheritanceStrategy()
	 * @generated
	 * @ordered
	 */
	protected InheritanceType inheritanceStrategy = INHERITANCE_STRATEGY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultDiscriminatorValue() <em>Default Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_DISCRIMINATOR_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultDiscriminatorValue() <em>Default Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected String defaultDiscriminatorValue = DEFAULT_DISCRIMINATOR_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpecifiedDiscriminatorValue() <em>Specified Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_DISCRIMINATOR_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedDiscriminatorValue() <em>Specified Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected String specifiedDiscriminatorValue = SPECIFIED_DISCRIMINATOR_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDiscriminatorValue() <em>Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DISCRIMINATOR_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDiscriminatorColumn() <em>Discriminator Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorColumn()
	 * @generated
	 * @ordered
	 */
	protected IDiscriminatorColumn discriminatorColumn;

	/**
	 * The cached value of the '{@link #getSequenceGenerator() <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceGenerator()
	 * @generated
	 * @ordered
	 */
	protected ISequenceGenerator sequenceGenerator;

	/**
	 * The cached value of the '{@link #getTableGenerator() <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTableGenerator()
	 * @generated
	 * @ordered
	 */
	protected ITableGenerator tableGenerator;

	/**
	 * The cached value of the '{@link #getSpecifiedAttributeOverrides() <em>Specified Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<IAttributeOverride> specifiedAttributeOverrides;

	/**
	 * The cached value of the '{@link #getDefaultAttributeOverrides() <em>Default Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<IAttributeOverride> defaultAttributeOverrides;

	/**
	 * The cached value of the '{@link #getSpecifiedAssociationOverrides() <em>Specified Association Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedAssociationOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<IAssociationOverride> specifiedAssociationOverrides;

	/**
	 * The cached value of the '{@link #getDefaultAssociationOverrides() <em>Default Association Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultAssociationOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<IAssociationOverride> defaultAssociationOverrides;

	/**
	 * The cached value of the '{@link #getNamedQueries() <em>Named Queries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedQueries()
	 * @generated
	 * @ordered
	 */
	protected EList<INamedQuery> namedQueries;

	/**
	 * The cached value of the '{@link #getNamedNativeQueries() <em>Named Native Queries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedNativeQueries()
	 * @generated
	 * @ordered
	 */
	protected EList<INamedNativeQuery> namedNativeQueries;

	/**
	 * The default value of the '{@link #getIdClass() <em>Id Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdClass()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIdClass() <em>Id Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdClass()
	 * @generated
	 * @ordered
	 */
	protected String idClass = ID_CLASS_EDEFAULT;

	private AnnotationElementAdapter<String> nameAdapter;

	private AnnotationElementAdapter<String> inheritanceStrategyAdapter;

	private final AnnotationElementAdapter<String> discriminatorValueAdapter;

	private AnnotationAdapter tableGeneratorAnnotationAdapter;

	private AnnotationAdapter sequenceGeneratorAnnotationAdapter;

	private final AnnotationAdapter idClassAnnotationAdapter;

	private final AnnotationElementAdapter<String> idClassValueAdapter;

	public static final DeclarationAnnotationAdapter ID_CLASS_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ID_CLASS);

	private static final DeclarationAnnotationElementAdapter<String> ID_CLASS_VALUE_ADAPTER = buildIdClassValueAdapter();

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ENTITY);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();

	private static final DeclarationAnnotationAdapter INHERITANCE_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.INHERITANCE);

	private static final DeclarationAnnotationElementAdapter<String> INHERITANCE_STRATEGY_ADAPTER = buildStrategyAdapter();

	private static final DeclarationAnnotationAdapter DISCRIMINATOR_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.DISCRIMINATOR_VALUE);

	private static final DeclarationAnnotationElementAdapter<String> DISCRIMINATOR_VALUE_ADAPTER = buildDiscriminatorValueAdapter();

	protected JavaEntity() {
		this(null);
	}

	protected JavaEntity(Type type) {
		super(type);
		this.table = JpaJavaMappingsFactory.eINSTANCE.createJavaTable(buildTableOwner(), getType());
		((InternalEObject) this.table).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ENTITY__TABLE, null, null);
		this.discriminatorColumn = JpaJavaMappingsFactory.eINSTANCE.createJavaDiscriminatorColumn(new IDiscriminatorColumn.Owner(this), type, JavaDiscriminatorColumn.DECLARATION_ANNOTATION_ADAPTER);
		((InternalEObject) this.discriminatorColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ENTITY__DISCRIMINATOR_COLUMN, null, null);
		//		this.getDefaultPrimaryKeyJoinColumns().add(this.createPrimaryKeyJoinColumn(IPrimaryKeyJoinColumnModelAdapter.DEFAULT));
		//		this.eAdapters().add(this.buildListener());
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(getType(), NAME_ADAPTER);
		this.inheritanceStrategyAdapter = new ShortCircuitAnnotationElementAdapter<String>(type, INHERITANCE_STRATEGY_ADAPTER);
		this.discriminatorValueAdapter = new ShortCircuitAnnotationElementAdapter<String>(type, DISCRIMINATOR_VALUE_ADAPTER);
		this.idClassAnnotationAdapter = new MemberAnnotationAdapter(this.getType(), ID_CLASS_ADAPTER);
		this.idClassValueAdapter = new ShortCircuitAnnotationElementAdapter<String>(this.getType(), ID_CLASS_VALUE_ADAPTER);
		this.getDefaultPrimaryKeyJoinColumns().add(this.createPrimaryKeyJoinColumn(0));
		this.tableGeneratorAnnotationAdapter = new MemberAnnotationAdapter(getType(), JavaTableGenerator.DECLARATION_ANNOTATION_ADAPTER);
		this.sequenceGeneratorAnnotationAdapter = new MemberAnnotationAdapter(getType(), JavaSequenceGenerator.DECLARATION_ANNOTATION_ADAPTER);
	}

	private ITable.Owner buildTableOwner() {
		return new ITable.Owner() {
			public ITextRange validationTextRange() {
				return JavaEntity.this.validationTextRange();
			}

			public ITypeMapping getTypeMapping() {
				return JavaEntity.this;
			}
		};
	}

	@Override
	public DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.ENTITY__NAME, false); // false = do not remove annotation when empty
	}

	/**
	 * check for changes to the 'specifiedJoinColumns' and
	 * 'specifiedInverseJoinColumns' lists so we can notify the
	 * model adapter of any changes;
	 * also listen for changes to the 'defaultJoinColumns' and
	 * 'defaultInverseJoinColumns' lists so we can spank the developer
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IEntity.class)) {
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_NAME :
				this.nameAdapter.setValue((String) notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.JAVA_ENTITY__INHERITANCE_STRATEGY :
				this.inheritanceStrategyAdapter.setValue(((InheritanceType) notification.getNewValue()).convertToJavaAnnotationValue());
				break;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_DISCRIMINATOR_VALUE :
				this.discriminatorValueAdapter.setValue((String) notification.getNewValue());
				break;
			case JpaCoreMappingsPackage.IENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES :
				this.attributeOverridesChanged(notification);
				break;
			case JpaCoreMappingsPackage.IENTITY__SPECIFIED_ASSOCIATION_OVERRIDES :
				this.associationOverridesChanged(notification);
				break;
			case JpaCoreMappingsPackage.IENTITY__SPECIFIED_SECONDARY_TABLES :
				this.secondaryTablesChanged(notification);
				break;
			case JpaCoreMappingsPackage.IENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				this.specifiedPrimaryKeyJoinColumnsChanged(notification);
				break;
			case JpaCoreMappingsPackage.IENTITY__NAMED_QUERIES :
				this.namedQueriesChanged(notification);
				break;
			case JpaCoreMappingsPackage.IENTITY__NAMED_NATIVE_QUERIES :
				this.namedNativeQueriesChanged(notification);
				break;
			case JpaCoreMappingsPackage.IENTITY__TABLE_GENERATOR :
				attributeChanged(notification.getNewValue(), this.tableGeneratorAnnotationAdapter);
				break;
			case JpaCoreMappingsPackage.IENTITY__SEQUENCE_GENERATOR :
				attributeChanged(notification.getNewValue(), this.sequenceGeneratorAnnotationAdapter);
				break;
			case JpaCoreMappingsPackage.IENTITY__ID_CLASS :
				String newIdClass = (String) notification.getNewValue();
				if (newIdClass == null) {
					this.idClassAnnotationAdapter.removeAnnotation();
				}
				else {
					this.idClassValueAdapter.setValue(newIdClass);
				}
			default :
				break;
		}
	}

	@SuppressWarnings("unchecked")
	void attributeOverridesChanged(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.ADD :
				attributeOverrideAdded(notification.getPosition(), (JavaAttributeOverride) notification.getNewValue());
				break;
			case Notification.ADD_MANY :
				attributeOverridesAdded(notification.getPosition(), (List<JavaAttributeOverride>) notification.getNewValue());
				break;
			case Notification.REMOVE :
				attributeOverrideRemoved(notification.getPosition(), (JavaAttributeOverride) notification.getOldValue());
				break;
			case Notification.REMOVE_MANY :
				if (notification.getPosition() == Notification.NO_INDEX) {
					attributeOverridesCleared((List<JavaAttributeOverride>) notification.getOldValue());
				}
				else {
					// Notification.getNewValue() returns an array of the positions of objects that were removed
					attributeOverridesRemoved((int[]) notification.getNewValue(), (List<JavaAttributeOverride>) notification.getOldValue());
				}
				break;
			case Notification.SET :
				if (!notification.isTouch()) {
					attributeOverrideSet(notification.getPosition(), (JavaAttributeOverride) notification.getOldValue(), (JavaAttributeOverride) notification.getNewValue());
				}
				break;
			case Notification.MOVE :
				// Notification.getOldValue() returns the source index
				// Notification.getPositon() returns the target index
				// Notification.getNewValue() returns the moved object
				attributeOverrideMoved(notification.getOldIntValue(), notification.getPosition(), (JavaAttributeOverride) notification.getNewValue());
				break;
			default :
				break;
		}
	}

	@SuppressWarnings("unchecked")
	void associationOverridesChanged(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.ADD :
				associationOverrideAdded(notification.getPosition(), (JavaAssociationOverride) notification.getNewValue());
				break;
			case Notification.ADD_MANY :
				associationOverridesAdded(notification.getPosition(), (List<JavaAssociationOverride>) notification.getNewValue());
				break;
			case Notification.REMOVE :
				associationOverrideRemoved(notification.getPosition(), (JavaAssociationOverride) notification.getOldValue());
				break;
			case Notification.REMOVE_MANY :
				if (notification.getPosition() == Notification.NO_INDEX) {
					associationOverridesCleared((List<JavaAssociationOverride>) notification.getOldValue());
				}
				else {
					// Notification.getNewValue() returns an array of the positions of objects that were removed
					associationOverridesRemoved((int[]) notification.getNewValue(), (List<JavaAssociationOverride>) notification.getOldValue());
				}
				break;
			case Notification.SET :
				if (!notification.isTouch()) {
					associationOverrideSet(notification.getPosition(), (JavaAssociationOverride) notification.getOldValue(), (JavaAssociationOverride) notification.getNewValue());
				}
				break;
			case Notification.MOVE :
				// Notification.getOldValue() returns the source index
				// Notification.getPositon() returns the target index
				// Notification.getNewValue() returns the moved object
				associationOverrideMoved(notification.getOldIntValue(), notification.getPosition(), (JavaAssociationOverride) notification.getNewValue());
				break;
			default :
				break;
		}
	}

	void defaultJoinColumnsChanged(Notification notification) {
		throw new IllegalStateException("'defaultJoinColumns' cannot be changed");
	}

	@SuppressWarnings("unchecked")
	void secondaryTablesChanged(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.ADD :
				secondaryTableAdded(notification.getPosition(), (JavaSecondaryTable) notification.getNewValue());
				break;
			case Notification.ADD_MANY :
				secondaryTablesAdded(notification.getPosition(), (List<ISecondaryTable>) notification.getNewValue());
				break;
			case Notification.REMOVE :
				secondaryTableRemoved(notification.getPosition(), (JavaSecondaryTable) notification.getOldValue());
				break;
			case Notification.REMOVE_MANY :
				if (notification.getPosition() == Notification.NO_INDEX) {
					secondaryTablesCleared((List<ISecondaryTable>) notification.getOldValue());
				}
				else {
					// Notification.getNewValue() returns an array of the positions of objects that were removed
					secondaryTablesRemoved((int[]) notification.getNewValue(), (List<ISecondaryTable>) notification.getOldValue());
				}
				break;
			case Notification.SET :
				if (!notification.isTouch()) {
					secondaryTableSet(notification.getPosition(), (JavaSecondaryTable) notification.getOldValue(), (JavaSecondaryTable) notification.getNewValue());
				}
				break;
			case Notification.MOVE :
				// Notification.getOldValue() returns the source index
				// Notification.getPositon() returns the target index
				// Notification.getNewValue() returns the moved object
				secondaryTableMoved(notification.getOldIntValue(), notification.getPosition(), (ISecondaryTable) notification.getNewValue());
				break;
			default :
				break;
		}
	}

	@SuppressWarnings("unchecked")
	void specifiedPrimaryKeyJoinColumnsChanged(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.ADD :
				specifiedPrimaryKeyJoinColumnAdded(notification.getPosition(), (JavaPrimaryKeyJoinColumn) notification.getNewValue());
				break;
			case Notification.ADD_MANY :
				specifiedPrimaryKeyJoinColumnsAdded(notification.getPosition(), (List<IPrimaryKeyJoinColumn>) notification.getNewValue());
				break;
			case Notification.REMOVE :
				specifiedPrimaryKeyJoinColumnRemoved(notification.getPosition(), (JavaPrimaryKeyJoinColumn) notification.getOldValue());
				break;
			case Notification.REMOVE_MANY :
				if (notification.getPosition() == Notification.NO_INDEX) {
					specifiedPrimaryKeyJoinColumnsCleared((List<IPrimaryKeyJoinColumn>) notification.getOldValue());
				}
				else {
					// Notification.getNewValue() returns an array of the positions of objects that were removed
					specifiedPrimaryKeyJoinColumnsRemoved((int[]) notification.getNewValue(), (List<IPrimaryKeyJoinColumn>) notification.getOldValue());
				}
				break;
			case Notification.SET :
				if (!notification.isTouch()) {
					specifiedPrimaryKeyJoinColumnSet(notification.getPosition(), (JavaPrimaryKeyJoinColumn) notification.getOldValue(), (JavaPrimaryKeyJoinColumn) notification.getNewValue());
				}
				break;
			case Notification.MOVE :
				// Notification.getOldValue() returns the source index
				// Notification.getPositon() returns the target index
				// Notification.getNewValue() returns the moved object
				specifiedPrimaryKeyJoinColumnMoved(notification.getOldIntValue(), notification.getPosition(), (JavaPrimaryKeyJoinColumn) notification.getNewValue());
				break;
			default :
				break;
		}
	}

	@SuppressWarnings("unchecked")
	void namedQueriesChanged(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.ADD :
				namedQueryAdded(notification.getPosition(), (JavaNamedQuery) notification.getNewValue());
				break;
			case Notification.ADD_MANY :
				namedQueriesAdded(notification.getPosition(), (List<JavaNamedQuery>) notification.getNewValue());
				break;
			case Notification.REMOVE :
				namedQueryRemoved(notification.getPosition(), (JavaNamedQuery) notification.getOldValue());
				break;
			case Notification.REMOVE_MANY :
				if (notification.getPosition() == Notification.NO_INDEX) {
					namedQueriesCleared((List<JavaNamedQuery>) notification.getOldValue());
				}
				else {
					// Notification.getNewValue() returns an array of the positions of objects that were removed
					namedQueriesRemoved((int[]) notification.getNewValue(), (List<JavaNamedQuery>) notification.getOldValue());
				}
				break;
			case Notification.SET :
				if (!notification.isTouch()) {
					namedQuerySet(notification.getPosition(), (JavaNamedQuery) notification.getOldValue(), (JavaNamedQuery) notification.getNewValue());
				}
				break;
			case Notification.MOVE :
				// Notification.getOldValue() returns the source index
				// Notification.getPositon() returns the target index
				// Notification.getNewValue() returns the moved object
				namedQueryMoved(notification.getOldIntValue(), notification.getPosition(), (JavaNamedQuery) notification.getNewValue());
				break;
			default :
				break;
		}
	}

	@SuppressWarnings("unchecked")
	void namedNativeQueriesChanged(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.ADD :
				namedNativeQueryAdded(notification.getPosition(), (JavaNamedNativeQuery) notification.getNewValue());
				break;
			case Notification.ADD_MANY :
				namedNativeQueriesAdded(notification.getPosition(), (List<JavaNamedNativeQuery>) notification.getNewValue());
				break;
			case Notification.REMOVE :
				namedNativeQueryRemoved(notification.getPosition(), (JavaNamedNativeQuery) notification.getOldValue());
				break;
			case Notification.REMOVE_MANY :
				if (notification.getPosition() == Notification.NO_INDEX) {
					namedNativeQueriesCleared((List<JavaNamedNativeQuery>) notification.getOldValue());
				}
				else {
					// Notification.getNewValue() returns an array of the positions of objects that were removed
					namedNativeQueriesRemoved((int[]) notification.getNewValue(), (List<JavaNamedNativeQuery>) notification.getOldValue());
				}
				break;
			case Notification.SET :
				if (!notification.isTouch()) {
					namedNativeQuerySet(notification.getPosition(), (JavaNamedNativeQuery) notification.getOldValue(), (JavaNamedNativeQuery) notification.getNewValue());
				}
				break;
			case Notification.MOVE :
				// Notification.getOldValue() returns the source index
				// Notification.getPositon() returns the target index
				// Notification.getNewValue() returns the moved object
				namedNativeQueryMoved(notification.getOldIntValue(), notification.getPosition(), (JavaNamedNativeQuery) notification.getNewValue());
				break;
			default :
				break;
		}
	}

	// ********** jpa model -> java annotations **********
	/**
	 * slide over all the annotations that follow the new attribute override
	 */
	public void attributeOverrideAdded(int index, JavaAttributeOverride attributeOverride) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (attributeOverride.annotation(getType().astRoot()) == null) {
			this.synchAttributeOverrideAnnotationsAfterAdd(index + 1);
			attributeOverride.newAnnotation();
		}
	}

	public void attributeOverridesAdded(int index, List<JavaAttributeOverride> attributeOverrides) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (!attributeOverrides.isEmpty() && (attributeOverrides.get(0).annotation(getType().astRoot()) == null)) {
			this.synchAttributeOverrideAnnotationsAfterAdd(index + attributeOverrides.size());
			for (JavaAttributeOverride attributeOverride : attributeOverrides) {
				attributeOverride.newAnnotation();
			}
		}
	}

	public void attributeOverrideRemoved(int index, JavaAttributeOverride attributeOverride) {
		attributeOverride.removeAnnotation();
		this.synchAttributeOverrideAnnotationsAfterRemove(index);
	}

	public void attributeOverridesRemoved(int[] indexes, List<JavaAttributeOverride> attributeOverrides) {
		for (JavaAttributeOverride attributeOverride : attributeOverrides) {
			attributeOverride.removeAnnotation();
		}
		this.synchAttributeOverrideAnnotationsAfterRemove(indexes[0]);
	}

	public void attributeOverridesCleared(List<JavaAttributeOverride> attributeOverrides) {
		for (JavaAttributeOverride attributeOverride : attributeOverrides) {
			attributeOverride.removeAnnotation();
		}
	}

	public void attributeOverrideSet(int index, JavaAttributeOverride oldAttributeOverride, JavaAttributeOverride newAttributeOverride) {
		newAttributeOverride.newAnnotation();
	}

	public void attributeOverrideMoved(int sourceIndex, int targetIndex, JavaAttributeOverride attributeOverride) {
		List<IAttributeOverride> attributeOverrides = getSpecifiedAttributeOverrides();
		int begin = Math.min(sourceIndex, targetIndex);
		int end = Math.max(sourceIndex, targetIndex);
		for (int i = begin; i-- > end;) {
			this.synch((JavaAttributeOverride) attributeOverrides.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchAttributeOverrideAnnotationsAfterAdd(int index) {
		List<IAttributeOverride> attributeOverrides = getSpecifiedAttributeOverrides();
		for (int i = attributeOverrides.size(); i-- > index;) {
			this.synch((JavaAttributeOverride) attributeOverrides.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchAttributeOverrideAnnotationsAfterRemove(int index) {
		List<IAttributeOverride> attributeOverrides = getSpecifiedAttributeOverrides();
		for (int i = index; i < attributeOverrides.size(); i++) {
			this.synch((JavaAttributeOverride) attributeOverrides.get(i), i);
		}
	}

	private void synch(JavaAttributeOverride attributeOverride, int index) {
		attributeOverride.moveAnnotation(index);
	}

	/**
	 * slide over all the annotations that follow the new association override
	 */
	public void associationOverrideAdded(int index, JavaAssociationOverride associationOverride) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (associationOverride.annotation(getType().astRoot()) == null) {
			this.synchAssociationOverrideAnnotationsAfterAdd(index + 1);
			associationOverride.newAnnotation();
		}
	}

	public void associationOverridesAdded(int index, List<JavaAssociationOverride> associationOverrides) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (!associationOverrides.isEmpty() && (associationOverrides.get(0).annotation(getType().astRoot()) == null)) {
			this.synchAssociationOverrideAnnotationsAfterAdd(index + associationOverrides.size());
			for (JavaAssociationOverride associationOverride : associationOverrides) {
				associationOverride.newAnnotation();
			}
		}
	}

	public void associationOverrideRemoved(int index, JavaAssociationOverride associationOverride) {
		associationOverride.removeAnnotation();
		this.synchAssociationOverrideAnnotationsAfterRemove(index);
	}

	public void associationOverridesRemoved(int[] indexes, List<JavaAssociationOverride> associationOverrides) {
		for (JavaAssociationOverride associationOverride : associationOverrides) {
			associationOverride.removeAnnotation();
		}
		this.synchAssociationOverrideAnnotationsAfterRemove(indexes[0]);
	}

	public void associationOverridesCleared(List<JavaAssociationOverride> associationOverrides) {
		for (JavaAssociationOverride associationOverride : associationOverrides) {
			associationOverride.removeAnnotation();
		}
	}

	public void associationOverrideSet(int index, JavaAssociationOverride oldAssociationOverride, JavaAssociationOverride newAssociationOverride) {
		newAssociationOverride.newAnnotation();
	}

	public void associationOverrideMoved(int sourceIndex, int targetIndex, JavaAssociationOverride associationOverride) {
		List<IAssociationOverride> assocationOverrides = getSpecifiedAssociationOverrides();
		int begin = Math.min(sourceIndex, targetIndex);
		int end = Math.max(sourceIndex, targetIndex);
		for (int i = begin; i-- > end;) {
			this.synch((JavaAssociationOverride) assocationOverrides.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchAssociationOverrideAnnotationsAfterAdd(int index) {
		List<IAssociationOverride> associationOverrides = getSpecifiedAssociationOverrides();
		for (int i = associationOverrides.size(); i-- > index;) {
			this.synch((JavaAssociationOverride) associationOverrides.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchAssociationOverrideAnnotationsAfterRemove(int index) {
		List<IAssociationOverride> assocationOverrides = getSpecifiedAssociationOverrides();
		for (int i = index; i < assocationOverrides.size(); i++) {
			this.synch((JavaAssociationOverride) assocationOverrides.get(i), i);
		}
	}

	private void synch(JavaAssociationOverride associationOverride, int index) {
		associationOverride.moveAnnotation(index);
	}

	/**
	 * slide over all the annotations that follow the new secondary table
	 */
	// bjv look at this
	public void secondaryTableAdded(int index, JavaSecondaryTable secondaryTable) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (secondaryTable.annotation(getType().astRoot()) == null) {
			this.synchSecondaryTableAnnotationsAfterAdd(index + 1);
			secondaryTable.newAnnotation();
		}
	}

	public void secondaryTablesAdded(int index, List<ISecondaryTable> secondaryTables) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (!secondaryTables.isEmpty() && ((JavaSecondaryTable) secondaryTables.get(0)).annotation(getType().astRoot()) == null) {
			this.synchSecondaryTableAnnotationsAfterAdd(index + secondaryTables.size());
			for (Iterator<ISecondaryTable> stream = secondaryTables.iterator(); stream.hasNext();) {
				JavaSecondaryTable secondaryTable = (JavaSecondaryTable) stream.next();
				secondaryTable.newAnnotation();
			}
		}
	}

	public void secondaryTableRemoved(int index, JavaSecondaryTable secondaryTable) {
		secondaryTable.removeAnnotation();
		this.synchSecondaryTableAnnotationsAfterRemove(index);
	}

	public void secondaryTablesRemoved(int[] indexes, List<ISecondaryTable> secondaryTables) {
		for (Iterator<ISecondaryTable> stream = secondaryTables.iterator(); stream.hasNext();) {
			JavaSecondaryTable secondaryTable = (JavaSecondaryTable) stream.next();
			secondaryTable.removeAnnotation();
		}
		this.synchSecondaryTableAnnotationsAfterRemove(indexes[0]);
	}

	public void secondaryTablesCleared(List<ISecondaryTable> secondaryTables) {
		for (Iterator<ISecondaryTable> stream = secondaryTables.iterator(); stream.hasNext();) {
			JavaSecondaryTable secondaryTable = (JavaSecondaryTable) stream.next();
			secondaryTable.removeAnnotation();
		}
	}

	public void secondaryTableSet(int index, JavaSecondaryTable oldSecondaryTable, JavaSecondaryTable newSecondaryTable) {
		newSecondaryTable.newAnnotation();
	}

	public void secondaryTableMoved(int sourceIndex, int targetIndex, ISecondaryTable secondaryTable) {
		List<ISecondaryTable> secondaryTables = getSecondaryTables();
		int begin = Math.min(sourceIndex, targetIndex);
		int end = Math.max(sourceIndex, targetIndex);
		for (int i = begin; i-- > end;) {
			this.synch((JavaSecondaryTable) secondaryTables.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchSecondaryTableAnnotationsAfterAdd(int index) {
		List<ISecondaryTable> secondaryTables = getSecondaryTables();
		for (int i = secondaryTables.size(); i-- > index;) {
			this.synch((JavaSecondaryTable) secondaryTables.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchSecondaryTableAnnotationsAfterRemove(int index) {
		List<ISecondaryTable> secondaryTables = getSecondaryTables();
		for (int i = index; i < secondaryTables.size(); i++) {
			this.synch((JavaSecondaryTable) secondaryTables.get(i), i);
		}
	}

	private void synch(JavaSecondaryTable secondaryTable, int index) {
		secondaryTable.moveAnnotation(index);
	}

	/**
	 * slide over all the annotations that follow the new
	 * primary key join column
	 */
	// bjv look at this
	public void specifiedPrimaryKeyJoinColumnAdded(int index, JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		if (primaryKeyJoinColumn.annotation(getType().astRoot()) == null) {
			this.synchPKJCAnnotationsAfterAdd(index + 1);
			primaryKeyJoinColumn.newAnnotation();
		}
	}

	public void specifiedPrimaryKeyJoinColumnsAdded(int index, List<IPrimaryKeyJoinColumn> primaryKeyJoinColumns) {
		if (!primaryKeyJoinColumns.isEmpty() && ((JavaPrimaryKeyJoinColumn) primaryKeyJoinColumns.get(0)).annotation(getType().astRoot()) == null) {
			this.synchPKJCAnnotationsAfterAdd(index + primaryKeyJoinColumns.size());
			for (Iterator<IPrimaryKeyJoinColumn> stream = primaryKeyJoinColumns.iterator(); stream.hasNext();) {
				JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = (JavaPrimaryKeyJoinColumn) stream.next();
				primaryKeyJoinColumn.newAnnotation();
			}
		}
	}

	public void specifiedPrimaryKeyJoinColumnRemoved(int index, JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		primaryKeyJoinColumn.removeAnnotation();
		this.synchPKJCAnnotationsAfterRemove(index);
	}

	public void specifiedPrimaryKeyJoinColumnsRemoved(int[] indexes, List<IPrimaryKeyJoinColumn> primaryKeyJoinColumns) {
		for (Iterator<IPrimaryKeyJoinColumn> stream = primaryKeyJoinColumns.iterator(); stream.hasNext();) {
			JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = (JavaPrimaryKeyJoinColumn) stream.next();
			primaryKeyJoinColumn.removeAnnotation();
		}
		this.synchPKJCAnnotationsAfterRemove(indexes[0]);
	}

	public void specifiedPrimaryKeyJoinColumnsCleared(List<IPrimaryKeyJoinColumn> primaryKeyJoinColumns) {
		for (Iterator<IPrimaryKeyJoinColumn> stream = primaryKeyJoinColumns.iterator(); stream.hasNext();) {
			JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = (JavaPrimaryKeyJoinColumn) stream.next();
			primaryKeyJoinColumn.removeAnnotation();
		}
	}

	public void specifiedPrimaryKeyJoinColumnSet(int index, JavaPrimaryKeyJoinColumn oldPrimaryKeyJoinColumn, JavaPrimaryKeyJoinColumn newPrimaryKeyJoinColumn) {
		newPrimaryKeyJoinColumn.newAnnotation();
	}

	public void specifiedPrimaryKeyJoinColumnMoved(int sourceIndex, int targetIndex, JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		List<IPrimaryKeyJoinColumn> primaryKeyJoinColumns = getSpecifiedPrimaryKeyJoinColumns();
		int begin = Math.min(sourceIndex, targetIndex);
		int end = Math.max(sourceIndex, targetIndex);
		for (int i = begin; i-- > end;) {
			this.synch((JavaPrimaryKeyJoinColumn) primaryKeyJoinColumns.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchPKJCAnnotationsAfterAdd(int index) {
		List<IPrimaryKeyJoinColumn> primaryKeyJoinColumns = getSpecifiedPrimaryKeyJoinColumns();
		for (int i = primaryKeyJoinColumns.size(); i-- > index;) {
			this.synch((JavaPrimaryKeyJoinColumn) primaryKeyJoinColumns.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchPKJCAnnotationsAfterRemove(int index) {
		List<IPrimaryKeyJoinColumn> primaryKeyJoinColumns = getSpecifiedPrimaryKeyJoinColumns();
		for (int i = index; i < primaryKeyJoinColumns.size(); i++) {
			this.synch((JavaPrimaryKeyJoinColumn) primaryKeyJoinColumns.get(i), i);
		}
	}

	private void synch(JavaPrimaryKeyJoinColumn primaryKeyJoinColumn, int index) {
		primaryKeyJoinColumn.moveAnnotation(index);
	}

	/**
	 * slide over all the annotations that follow the new secondary table
	 */
	// bjv look at this
	public void namedQueryAdded(int index, JavaNamedQuery namedQuery) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (namedQuery.annotation(getType().astRoot()) == null) {
			this.synchNamedQueryAnnotationsAfterAdd(index + 1);
			namedQuery.newAnnotation();
		}
	}

	public void namedQueriesAdded(int index, List<JavaNamedQuery> queries) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (!queries.isEmpty() && queries.get(0).annotation(getType().astRoot()) == null) {
			this.synchNamedQueryAnnotationsAfterAdd(index + queries.size());
			for (JavaNamedQuery namedQuery : queries) {
				namedQuery.newAnnotation();
			}
		}
	}

	public void namedQueryRemoved(int index, JavaNamedQuery namedQuery) {
		namedQuery.removeAnnotation();
		this.synchNamedQueryAnnotationsAfterRemove(index);
	}

	public void namedQueriesRemoved(int[] indexes, List<JavaNamedQuery> queries) {
		for (JavaNamedQuery namedQuery : queries) {
			namedQuery.removeAnnotation();
		}
		this.synchNamedQueryAnnotationsAfterRemove(indexes[0]);
	}

	public void namedQueriesCleared(List<JavaNamedQuery> queries) {
		for (JavaNamedQuery namedQuery : queries) {
			namedQuery.removeAnnotation();
		}
	}

	public void namedQuerySet(int index, JavaNamedQuery oldNamedQuery, JavaNamedQuery newNamedQuery) {
		newNamedQuery.newAnnotation();
	}

	public void namedQueryMoved(int sourceIndex, int targetIndex, JavaNamedQuery namedQuery) {
		List<INamedQuery> queries = getNamedQueries();
		int begin = Math.min(sourceIndex, targetIndex);
		int end = Math.max(sourceIndex, targetIndex);
		for (int i = begin; i-- > end;) {
			this.synch((JavaNamedQuery) queries.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchNamedQueryAnnotationsAfterAdd(int index) {
		List<INamedQuery> queries = getNamedQueries();
		for (int i = queries.size(); i-- > index;) {
			this.synch((JavaNamedQuery) queries.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchNamedQueryAnnotationsAfterRemove(int index) {
		List<INamedQuery> queries = getNamedQueries();
		for (int i = index; i < queries.size(); i++) {
			this.synch((JavaNamedQuery) queries.get(i), i);
		}
	}

	private void synch(JavaNamedQuery namedQuery, int index) {
		namedQuery.moveAnnotation(index);
	}

	/**
	 * slide over all the annotations that follow the new secondary table
	 */
	// bjv look at this
	public void namedNativeQueryAdded(int index, JavaNamedNativeQuery namedQuery) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (namedQuery.annotation(getType().astRoot()) == null) {
			this.synchNamedNativeQueryAnnotationsAfterAdd(index + 1);
			namedQuery.newAnnotation();
		}
	}

	public void namedNativeQueriesAdded(int index, List<JavaNamedNativeQuery> queries) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (!queries.isEmpty() && queries.get(0).annotation(getType().astRoot()) == null) {
			this.synchNamedNativeQueryAnnotationsAfterAdd(index + queries.size());
			for (JavaNamedNativeQuery namedQuery : queries) {
				namedQuery.newAnnotation();
			}
		}
	}

	public void namedNativeQueryRemoved(int index, JavaNamedNativeQuery namedQuery) {
		namedQuery.removeAnnotation();
		this.synchNamedNativeQueryAnnotationsAfterRemove(index);
	}

	public void namedNativeQueriesRemoved(int[] indexes, List<JavaNamedNativeQuery> queries) {
		for (JavaNamedNativeQuery namedQuery : queries) {
			namedQuery.removeAnnotation();
		}
		this.synchNamedNativeQueryAnnotationsAfterRemove(indexes[0]);
	}

	public void namedNativeQueriesCleared(List<JavaNamedNativeQuery> queries) {
		for (JavaNamedNativeQuery namedQuery : queries) {
			namedQuery.removeAnnotation();
		}
	}

	public void namedNativeQuerySet(int index, JavaNamedNativeQuery oldNamedQuery, JavaNamedNativeQuery newNamedQuery) {
		newNamedQuery.newAnnotation();
	}

	public void namedNativeQueryMoved(int sourceIndex, int targetIndex, JavaNamedNativeQuery namedQuery) {
		List<INamedNativeQuery> queries = getNamedNativeQueries();
		int begin = Math.min(sourceIndex, targetIndex);
		int end = Math.max(sourceIndex, targetIndex);
		for (int i = begin; i-- > end;) {
			this.synch((JavaNamedNativeQuery) queries.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchNamedNativeQueryAnnotationsAfterAdd(int index) {
		List<INamedNativeQuery> queries = getNamedNativeQueries();
		for (int i = queries.size(); i-- > index;) {
			this.synch((JavaNamedNativeQuery) queries.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchNamedNativeQueryAnnotationsAfterRemove(int index) {
		List<INamedNativeQuery> queries = getNamedNativeQueries();
		for (int i = index; i < queries.size(); i++) {
			this.synch((JavaNamedNativeQuery) queries.get(i), i);
		}
	}

	private void synch(JavaNamedNativeQuery namedQuery, int index) {
		namedQuery.moveAnnotation(index);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_ENTITY;
	}

	@Override
	public String getName() {
		return (this.getSpecifiedName() == null) ? this.getDefaultName() : this.getSpecifiedName();
	}

	/**
	 * Returns the value of the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Name</em>' attribute.
	 * @see #setSpecifiedName(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_SpecifiedName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedName() {
		return specifiedName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity#getSpecifiedName <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Name</em>' attribute.
	 * @see #getSpecifiedName()
	 * @generated
	 */
	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = specifiedName;
		specifiedName = newSpecifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_NAME, oldSpecifiedName, specifiedName));
	}

	/**
	 * Returns the value of the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_DefaultName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultName() {
		return defaultName;
	}

	/**
	 * Returns the value of the '<em><b>Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_Table()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	public ITable getTable() {
		return table;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTable(ITable newTable, NotificationChain msgs) {
		ITable oldTable = table;
		table = newTable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__TABLE, oldTable, newTable);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Returns the value of the '<em><b>Specified Secondary Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Secondary Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Secondary Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_SpecifiedSecondaryTables()
	 * @model type="org.eclipse.jpt.core.internal.mappings.ISecondaryTable" containment="true"
	 * @generated
	 */
	public EList<ISecondaryTable> getSpecifiedSecondaryTables() {
		if (specifiedSecondaryTables == null) {
			specifiedSecondaryTables = new EObjectContainmentEList<ISecondaryTable>(ISecondaryTable.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_SECONDARY_TABLES);
		}
		return specifiedSecondaryTables;
	}

	public EList<ISecondaryTable> getSecondaryTables() {
		return getSpecifiedSecondaryTables();
	}

	public boolean containsSecondaryTable(String name) {
		return containsSecondaryTable(name, getSecondaryTables());
	}

	public boolean containsSpecifiedSecondaryTable(String name) {
		return containsSecondaryTable(name, getSpecifiedSecondaryTables());
	}

	private boolean containsSecondaryTable(String name, List<ISecondaryTable> secondaryTables) {
		for (ISecondaryTable secondaryTable : secondaryTables) {
			String secondaryTableName = secondaryTable.getName();
			if (secondaryTableName != null && secondaryTableName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the value of the '<em><b>Inheritance Strategy</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.InheritanceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inheritance Strategy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inheritance Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.InheritanceType
	 * @see #setInheritanceStrategy(InheritanceType)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_InheritanceStrategy()
	 * @model
	 * @generated
	 */
	public InheritanceType getInheritanceStrategy() {
		return inheritanceStrategy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity#getInheritanceStrategy <em>Inheritance Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inheritance Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.InheritanceType
	 * @see #getInheritanceStrategy()
	 * @generated
	 */
	public void setInheritanceStrategy(InheritanceType newInheritanceStrategy) {
		InheritanceType oldInheritanceStrategy = inheritanceStrategy;
		inheritanceStrategy = newInheritanceStrategy == null ? INHERITANCE_STRATEGY_EDEFAULT : newInheritanceStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__INHERITANCE_STRATEGY, oldInheritanceStrategy, inheritanceStrategy));
	}

	/**
	 * Returns the value of the '<em><b>Discriminator Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Column</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_DiscriminatorColumn()
	 * @model containment="true" changeable="false"
	 * @generated
	 */
	public IDiscriminatorColumn getDiscriminatorColumn() {
		return discriminatorColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDiscriminatorColumn(IDiscriminatorColumn newDiscriminatorColumn, NotificationChain msgs) {
		IDiscriminatorColumn oldDiscriminatorColumn = discriminatorColumn;
		discriminatorColumn = newDiscriminatorColumn;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__DISCRIMINATOR_COLUMN, oldDiscriminatorColumn, newDiscriminatorColumn);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Returns the value of the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Generator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #setSequenceGenerator(ISequenceGenerator)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_SequenceGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public ISequenceGenerator getSequenceGenerator() {
		return sequenceGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSequenceGenerator(ISequenceGenerator newSequenceGenerator, NotificationChain msgs) {
		ISequenceGenerator oldSequenceGenerator = sequenceGenerator;
		sequenceGenerator = newSequenceGenerator;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR, oldSequenceGenerator, newSequenceGenerator);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity#getSequenceGenerator <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #getSequenceGenerator()
	 * @generated
	 */
	public void setSequenceGenerator(ISequenceGenerator newSequenceGenerator) {
		if (newSequenceGenerator != sequenceGenerator) {
			NotificationChain msgs = null;
			if (sequenceGenerator != null)
				msgs = ((InternalEObject) sequenceGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR, null, msgs);
			if (newSequenceGenerator != null)
				msgs = ((InternalEObject) newSequenceGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR, null, msgs);
			msgs = basicSetSequenceGenerator(newSequenceGenerator, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR, newSequenceGenerator, newSequenceGenerator));
	}

	/**
	 * Returns the value of the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Generator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Generator</em>' containment reference.
	 * @see #setTableGenerator(ITableGenerator)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_TableGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public ITableGenerator getTableGenerator() {
		return tableGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTableGenerator(ITableGenerator newTableGenerator, NotificationChain msgs) {
		ITableGenerator oldTableGenerator = tableGenerator;
		tableGenerator = newTableGenerator;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR, oldTableGenerator, newTableGenerator);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity#getTableGenerator <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table Generator</em>' containment reference.
	 * @see #getTableGenerator()
	 * @generated
	 */
	public void setTableGenerator(ITableGenerator newTableGenerator) {
		if (newTableGenerator != tableGenerator) {
			NotificationChain msgs = null;
			if (tableGenerator != null)
				msgs = ((InternalEObject) tableGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR, null, msgs);
			if (newTableGenerator != null)
				msgs = ((InternalEObject) newTableGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR, null, msgs);
			msgs = basicSetTableGenerator(newTableGenerator, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR, newTableGenerator, newTableGenerator));
	}

	/**
	 * Returns the value of the '<em><b>Default Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Discriminator Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Discriminator Value</em>' attribute.
	 * @see #setDefaultDiscriminatorValue(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_DefaultDiscriminatorValue()
	 * @model
	 * @generated
	 */
	public String getDefaultDiscriminatorValue() {
		return defaultDiscriminatorValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity#getDefaultDiscriminatorValue <em>Default Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Discriminator Value</em>' attribute.
	 * @see #getDefaultDiscriminatorValue()
	 * @generated
	 */
	public void setDefaultDiscriminatorValue(String newDefaultDiscriminatorValue) {
		String oldDefaultDiscriminatorValue = defaultDiscriminatorValue;
		defaultDiscriminatorValue = newDefaultDiscriminatorValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_DISCRIMINATOR_VALUE, oldDefaultDiscriminatorValue, defaultDiscriminatorValue));
	}

	/**
	 * Returns the value of the '<em><b>Specified Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Discriminator Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Discriminator Value</em>' attribute.
	 * @see #setSpecifiedDiscriminatorValue(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_SpecifiedDiscriminatorValue()
	 * @model
	 * @generated
	 */
	public String getSpecifiedDiscriminatorValue() {
		return specifiedDiscriminatorValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity#getSpecifiedDiscriminatorValue <em>Specified Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Discriminator Value</em>' attribute.
	 * @see #getSpecifiedDiscriminatorValue()
	 * @generated
	 */
	public void setSpecifiedDiscriminatorValue(String newSpecifiedDiscriminatorValue) {
		String oldSpecifiedDiscriminatorValue = specifiedDiscriminatorValue;
		specifiedDiscriminatorValue = newSpecifiedDiscriminatorValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_DISCRIMINATOR_VALUE, oldSpecifiedDiscriminatorValue, specifiedDiscriminatorValue));
	}

	/**
	 * Returns the value of the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Value</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_DiscriminatorValue()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getDiscriminatorValue() {
		return (this.getSpecifiedDiscriminatorValue() == null) ? getDefaultDiscriminatorValue() : this.getSpecifiedDiscriminatorValue();
	}

	public EList<IPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumns().isEmpty() ? this.getDefaultPrimaryKeyJoinColumns() : this.getSpecifiedPrimaryKeyJoinColumns();
	}

	/**
	 * Returns the value of the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_SpecifiedPrimaryKeyJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn" containment="true"
	 * @generated
	 */
	public EList<IPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		if (specifiedPrimaryKeyJoinColumns == null) {
			specifiedPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS);
		}
		return specifiedPrimaryKeyJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_DefaultPrimaryKeyJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn" containment="true"
	 * @generated
	 */
	public EList<IPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
		if (defaultPrimaryKeyJoinColumns == null) {
			defaultPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS);
		}
		return defaultPrimaryKeyJoinColumns;
	}

	public EList<IAttributeOverride> getAttributeOverrides() {
		EList<IAttributeOverride> list = new EObjectEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__ATTRIBUTE_OVERRIDES);
		list.addAll(getSpecifiedAttributeOverrides());
		list.addAll(getDefaultAttributeOverrides());
		return list;
	}

	public boolean containsAttributeOverride(String name) {
		return containsOverride(name, getAttributeOverrides());
	}

	public boolean containsSpecifiedAttributeOverride(String name) {
		return containsOverride(name, getSpecifiedAttributeOverrides());
	}

	public boolean containsAssociationOverride(String name) {
		return containsOverride(name, getAssociationOverrides());
	}

	public boolean containsSpecifiedAssociationOverride(String name) {
		return containsOverride(name, getSpecifiedAssociationOverrides());
	}

	private boolean containsOverride(String name, List<? extends IOverride> overrides) {
		for (IOverride override : overrides) {
			String overrideName = override.getName();
			if (overrideName == null && name == null) {
				return true;
			}
			if (overrideName != null && overrideName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the value of the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Attribute Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_SpecifiedAttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true"
	 * @generated
	 */
	public EList<IAttributeOverride> getSpecifiedAttributeOverrides() {
		if (specifiedAttributeOverrides == null) {
			specifiedAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES);
		}
		return specifiedAttributeOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Attribute Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_DefaultAttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true"
	 * @generated
	 */
	public EList<IAttributeOverride> getDefaultAttributeOverrides() {
		if (defaultAttributeOverrides == null) {
			defaultAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ATTRIBUTE_OVERRIDES);
		}
		return defaultAttributeOverrides;
	}

	public EList<IAssociationOverride> getAssociationOverrides() {
		EList<IAssociationOverride> list = new EObjectEList<IAssociationOverride>(IAssociationOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__ASSOCIATION_OVERRIDES);
		list.addAll(getSpecifiedAssociationOverrides());
		list.addAll(getDefaultAssociationOverrides());
		return list;
	}

	/**
	 * Returns the value of the '<em><b>Specified Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Association Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_SpecifiedAssociationOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAssociationOverride" containment="true"
	 * @generated
	 */
	public EList<IAssociationOverride> getSpecifiedAssociationOverrides() {
		if (specifiedAssociationOverrides == null) {
			specifiedAssociationOverrides = new EObjectContainmentEList<IAssociationOverride>(IAssociationOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ASSOCIATION_OVERRIDES);
		}
		return specifiedAssociationOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Default Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Association Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_DefaultAssociationOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAssociationOverride" containment="true"
	 * @generated
	 */
	public EList<IAssociationOverride> getDefaultAssociationOverrides() {
		if (defaultAssociationOverrides == null) {
			defaultAssociationOverrides = new EObjectContainmentEList<IAssociationOverride>(IAssociationOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ASSOCIATION_OVERRIDES);
		}
		return defaultAssociationOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Named Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.INamedQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_NamedQueries()
	 * @model type="org.eclipse.jpt.core.internal.mappings.INamedQuery" containment="true"
	 * @generated
	 */
	public EList<INamedQuery> getNamedQueries() {
		if (namedQueries == null) {
			namedQueries = new EObjectContainmentEList<INamedQuery>(INamedQuery.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_QUERIES);
		}
		return namedQueries;
	}

	/**
	 * Returns the value of the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.INamedNativeQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Native Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Native Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_NamedNativeQueries()
	 * @model type="org.eclipse.jpt.core.internal.mappings.INamedNativeQuery" containment="true"
	 * @generated
	 */
	public EList<INamedNativeQuery> getNamedNativeQueries() {
		if (namedNativeQueries == null) {
			namedNativeQueries = new EObjectContainmentEList<INamedNativeQuery>(INamedNativeQuery.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_NATIVE_QUERIES);
		}
		return namedNativeQueries;
	}

	/**
	 * Returns the value of the '<em><b>Id Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Class</em>' attribute.
	 * @see #setIdClass(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIEntity_IdClass()
	 * @model
	 * @generated
	 */
	public String getIdClass() {
		return idClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity#getIdClass <em>Id Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Class</em>' attribute.
	 * @see #getIdClass()
	 * @generated
	 */
	public void setIdClass(String newIdClass) {
		String oldIdClass = idClass;
		idClass = newIdClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__ID_CLASS, oldIdClass, idClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public boolean discriminatorValueIsAllowed() {
		return !getType().isAbstract();
	}

	public IEntity parentEntity() {
		for (Iterator<IPersistentType> i = getPersistentType().inheritanceHierarchy(); i.hasNext();) {
			ITypeMapping typeMapping = i.next().getMapping();
			if (typeMapping != this && typeMapping instanceof IEntity) {
				return (IEntity) typeMapping;
			}
		}
		return this;
	}

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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ENTITY__TABLE :
				return basicSetTable(null, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_SECONDARY_TABLES :
				return ((InternalEList<?>) getSpecifiedSecondaryTables()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__PRIMARY_KEY_JOIN_COLUMNS :
				return ((InternalEList<?>) getPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				return ((InternalEList<?>) getSpecifiedPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				return ((InternalEList<?>) getDefaultPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__DISCRIMINATOR_COLUMN :
				return basicSetDiscriminatorColumn(null, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR :
				return basicSetSequenceGenerator(null, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR :
				return basicSetTableGenerator(null, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__ATTRIBUTE_OVERRIDES :
				return ((InternalEList<?>) getAttributeOverrides()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES :
				return ((InternalEList<?>) getSpecifiedAttributeOverrides()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ATTRIBUTE_OVERRIDES :
				return ((InternalEList<?>) getDefaultAttributeOverrides()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__ASSOCIATION_OVERRIDES :
				return ((InternalEList<?>) getAssociationOverrides()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ASSOCIATION_OVERRIDES :
				return ((InternalEList<?>) getSpecifiedAssociationOverrides()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ASSOCIATION_OVERRIDES :
				return ((InternalEList<?>) getDefaultAssociationOverrides()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_QUERIES :
				return ((InternalEList<?>) getNamedQueries()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_NATIVE_QUERIES :
				return ((InternalEList<?>) getNamedNativeQueries()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = defaultName;
		defaultName = newDefaultName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_NAME, oldDefaultName, defaultName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_NAME :
				return getSpecifiedName();
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_NAME :
				return getDefaultName();
			case JpaJavaMappingsPackage.JAVA_ENTITY__TABLE :
				return getTable();
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_SECONDARY_TABLES :
				return getSpecifiedSecondaryTables();
			case JpaJavaMappingsPackage.JAVA_ENTITY__PRIMARY_KEY_JOIN_COLUMNS :
				return getPrimaryKeyJoinColumns();
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				return getSpecifiedPrimaryKeyJoinColumns();
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				return getDefaultPrimaryKeyJoinColumns();
			case JpaJavaMappingsPackage.JAVA_ENTITY__INHERITANCE_STRATEGY :
				return getInheritanceStrategy();
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_DISCRIMINATOR_VALUE :
				return getDefaultDiscriminatorValue();
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_DISCRIMINATOR_VALUE :
				return getSpecifiedDiscriminatorValue();
			case JpaJavaMappingsPackage.JAVA_ENTITY__DISCRIMINATOR_VALUE :
				return getDiscriminatorValue();
			case JpaJavaMappingsPackage.JAVA_ENTITY__DISCRIMINATOR_COLUMN :
				return getDiscriminatorColumn();
			case JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR :
				return getSequenceGenerator();
			case JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR :
				return getTableGenerator();
			case JpaJavaMappingsPackage.JAVA_ENTITY__ATTRIBUTE_OVERRIDES :
				return getAttributeOverrides();
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES :
				return getSpecifiedAttributeOverrides();
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ATTRIBUTE_OVERRIDES :
				return getDefaultAttributeOverrides();
			case JpaJavaMappingsPackage.JAVA_ENTITY__ASSOCIATION_OVERRIDES :
				return getAssociationOverrides();
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ASSOCIATION_OVERRIDES :
				return getSpecifiedAssociationOverrides();
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ASSOCIATION_OVERRIDES :
				return getDefaultAssociationOverrides();
			case JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_QUERIES :
				return getNamedQueries();
			case JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_NATIVE_QUERIES :
				return getNamedNativeQueries();
			case JpaJavaMappingsPackage.JAVA_ENTITY__ID_CLASS :
				return getIdClass();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_NAME :
				setSpecifiedName((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_SECONDARY_TABLES :
				getSpecifiedSecondaryTables().clear();
				getSpecifiedSecondaryTables().addAll((Collection<? extends ISecondaryTable>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				getSpecifiedPrimaryKeyJoinColumns().clear();
				getSpecifiedPrimaryKeyJoinColumns().addAll((Collection<? extends IPrimaryKeyJoinColumn>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				getDefaultPrimaryKeyJoinColumns().clear();
				getDefaultPrimaryKeyJoinColumns().addAll((Collection<? extends IPrimaryKeyJoinColumn>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__INHERITANCE_STRATEGY :
				setInheritanceStrategy((InheritanceType) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_DISCRIMINATOR_VALUE :
				setDefaultDiscriminatorValue((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_DISCRIMINATOR_VALUE :
				setSpecifiedDiscriminatorValue((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR :
				setSequenceGenerator((ISequenceGenerator) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR :
				setTableGenerator((ITableGenerator) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES :
				getSpecifiedAttributeOverrides().clear();
				getSpecifiedAttributeOverrides().addAll((Collection<? extends IAttributeOverride>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ATTRIBUTE_OVERRIDES :
				getDefaultAttributeOverrides().clear();
				getDefaultAttributeOverrides().addAll((Collection<? extends IAttributeOverride>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ASSOCIATION_OVERRIDES :
				getSpecifiedAssociationOverrides().clear();
				getSpecifiedAssociationOverrides().addAll((Collection<? extends IAssociationOverride>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ASSOCIATION_OVERRIDES :
				getDefaultAssociationOverrides().clear();
				getDefaultAssociationOverrides().addAll((Collection<? extends IAssociationOverride>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_QUERIES :
				getNamedQueries().clear();
				getNamedQueries().addAll((Collection<? extends INamedQuery>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_NATIVE_QUERIES :
				getNamedNativeQueries().clear();
				getNamedNativeQueries().addAll((Collection<? extends INamedNativeQuery>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__ID_CLASS :
				setIdClass((String) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_NAME :
				setSpecifiedName(SPECIFIED_NAME_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_SECONDARY_TABLES :
				getSpecifiedSecondaryTables().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				getSpecifiedPrimaryKeyJoinColumns().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				getDefaultPrimaryKeyJoinColumns().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__INHERITANCE_STRATEGY :
				setInheritanceStrategy(INHERITANCE_STRATEGY_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_DISCRIMINATOR_VALUE :
				setDefaultDiscriminatorValue(DEFAULT_DISCRIMINATOR_VALUE_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_DISCRIMINATOR_VALUE :
				setSpecifiedDiscriminatorValue(SPECIFIED_DISCRIMINATOR_VALUE_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR :
				setSequenceGenerator((ISequenceGenerator) null);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR :
				setTableGenerator((ITableGenerator) null);
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES :
				getSpecifiedAttributeOverrides().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ATTRIBUTE_OVERRIDES :
				getDefaultAttributeOverrides().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ASSOCIATION_OVERRIDES :
				getSpecifiedAssociationOverrides().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ASSOCIATION_OVERRIDES :
				getDefaultAssociationOverrides().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_QUERIES :
				getNamedQueries().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_NATIVE_QUERIES :
				getNamedNativeQueries().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_ENTITY__ID_CLASS :
				setIdClass(ID_CLASS_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_NAME :
				return SPECIFIED_NAME_EDEFAULT == null ? specifiedName != null : !SPECIFIED_NAME_EDEFAULT.equals(specifiedName);
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_NAME :
				return DEFAULT_NAME_EDEFAULT == null ? defaultName != null : !DEFAULT_NAME_EDEFAULT.equals(defaultName);
			case JpaJavaMappingsPackage.JAVA_ENTITY__TABLE :
				return table != null;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_SECONDARY_TABLES :
				return specifiedSecondaryTables != null && !specifiedSecondaryTables.isEmpty();
			case JpaJavaMappingsPackage.JAVA_ENTITY__PRIMARY_KEY_JOIN_COLUMNS :
				return !getPrimaryKeyJoinColumns().isEmpty();
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				return specifiedPrimaryKeyJoinColumns != null && !specifiedPrimaryKeyJoinColumns.isEmpty();
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				return defaultPrimaryKeyJoinColumns != null && !defaultPrimaryKeyJoinColumns.isEmpty();
			case JpaJavaMappingsPackage.JAVA_ENTITY__INHERITANCE_STRATEGY :
				return inheritanceStrategy != INHERITANCE_STRATEGY_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_DISCRIMINATOR_VALUE :
				return DEFAULT_DISCRIMINATOR_VALUE_EDEFAULT == null ? defaultDiscriminatorValue != null : !DEFAULT_DISCRIMINATOR_VALUE_EDEFAULT.equals(defaultDiscriminatorValue);
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_DISCRIMINATOR_VALUE :
				return SPECIFIED_DISCRIMINATOR_VALUE_EDEFAULT == null ? specifiedDiscriminatorValue != null : !SPECIFIED_DISCRIMINATOR_VALUE_EDEFAULT.equals(specifiedDiscriminatorValue);
			case JpaJavaMappingsPackage.JAVA_ENTITY__DISCRIMINATOR_VALUE :
				return DISCRIMINATOR_VALUE_EDEFAULT == null ? getDiscriminatorValue() != null : !DISCRIMINATOR_VALUE_EDEFAULT.equals(getDiscriminatorValue());
			case JpaJavaMappingsPackage.JAVA_ENTITY__DISCRIMINATOR_COLUMN :
				return discriminatorColumn != null;
			case JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR :
				return sequenceGenerator != null;
			case JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR :
				return tableGenerator != null;
			case JpaJavaMappingsPackage.JAVA_ENTITY__ATTRIBUTE_OVERRIDES :
				return !getAttributeOverrides().isEmpty();
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES :
				return specifiedAttributeOverrides != null && !specifiedAttributeOverrides.isEmpty();
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ATTRIBUTE_OVERRIDES :
				return defaultAttributeOverrides != null && !defaultAttributeOverrides.isEmpty();
			case JpaJavaMappingsPackage.JAVA_ENTITY__ASSOCIATION_OVERRIDES :
				return !getAssociationOverrides().isEmpty();
			case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ASSOCIATION_OVERRIDES :
				return specifiedAssociationOverrides != null && !specifiedAssociationOverrides.isEmpty();
			case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ASSOCIATION_OVERRIDES :
				return defaultAssociationOverrides != null && !defaultAssociationOverrides.isEmpty();
			case JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_QUERIES :
				return namedQueries != null && !namedQueries.isEmpty();
			case JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_NATIVE_QUERIES :
				return namedNativeQueries != null && !namedNativeQueries.isEmpty();
			case JpaJavaMappingsPackage.JAVA_ENTITY__ID_CLASS :
				return ID_CLASS_EDEFAULT == null ? idClass != null : !ID_CLASS_EDEFAULT.equals(idClass);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IEntity.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_NAME :
					return JpaCoreMappingsPackage.IENTITY__SPECIFIED_NAME;
				case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_NAME :
					return JpaCoreMappingsPackage.IENTITY__DEFAULT_NAME;
				case JpaJavaMappingsPackage.JAVA_ENTITY__TABLE :
					return JpaCoreMappingsPackage.IENTITY__TABLE;
				case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_SECONDARY_TABLES :
					return JpaCoreMappingsPackage.IENTITY__SPECIFIED_SECONDARY_TABLES;
				case JpaJavaMappingsPackage.JAVA_ENTITY__PRIMARY_KEY_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IENTITY__PRIMARY_KEY_JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_ENTITY__INHERITANCE_STRATEGY :
					return JpaCoreMappingsPackage.IENTITY__INHERITANCE_STRATEGY;
				case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_DISCRIMINATOR_VALUE :
					return JpaCoreMappingsPackage.IENTITY__DEFAULT_DISCRIMINATOR_VALUE;
				case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_DISCRIMINATOR_VALUE :
					return JpaCoreMappingsPackage.IENTITY__SPECIFIED_DISCRIMINATOR_VALUE;
				case JpaJavaMappingsPackage.JAVA_ENTITY__DISCRIMINATOR_VALUE :
					return JpaCoreMappingsPackage.IENTITY__DISCRIMINATOR_VALUE;
				case JpaJavaMappingsPackage.JAVA_ENTITY__DISCRIMINATOR_COLUMN :
					return JpaCoreMappingsPackage.IENTITY__DISCRIMINATOR_COLUMN;
				case JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR :
					return JpaCoreMappingsPackage.IENTITY__SEQUENCE_GENERATOR;
				case JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR :
					return JpaCoreMappingsPackage.IENTITY__TABLE_GENERATOR;
				case JpaJavaMappingsPackage.JAVA_ENTITY__ATTRIBUTE_OVERRIDES :
					return JpaCoreMappingsPackage.IENTITY__ATTRIBUTE_OVERRIDES;
				case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES :
					return JpaCoreMappingsPackage.IENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES;
				case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ATTRIBUTE_OVERRIDES :
					return JpaCoreMappingsPackage.IENTITY__DEFAULT_ATTRIBUTE_OVERRIDES;
				case JpaJavaMappingsPackage.JAVA_ENTITY__ASSOCIATION_OVERRIDES :
					return JpaCoreMappingsPackage.IENTITY__ASSOCIATION_OVERRIDES;
				case JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ASSOCIATION_OVERRIDES :
					return JpaCoreMappingsPackage.IENTITY__SPECIFIED_ASSOCIATION_OVERRIDES;
				case JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ASSOCIATION_OVERRIDES :
					return JpaCoreMappingsPackage.IENTITY__DEFAULT_ASSOCIATION_OVERRIDES;
				case JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_QUERIES :
					return JpaCoreMappingsPackage.IENTITY__NAMED_QUERIES;
				case JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_NATIVE_QUERIES :
					return JpaCoreMappingsPackage.IENTITY__NAMED_NATIVE_QUERIES;
				case JpaJavaMappingsPackage.JAVA_ENTITY__ID_CLASS :
					return JpaCoreMappingsPackage.IENTITY__ID_CLASS;
				default :
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IEntity.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IENTITY__SPECIFIED_NAME :
					return JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_NAME;
				case JpaCoreMappingsPackage.IENTITY__DEFAULT_NAME :
					return JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_NAME;
				case JpaCoreMappingsPackage.IENTITY__TABLE :
					return JpaJavaMappingsPackage.JAVA_ENTITY__TABLE;
				case JpaCoreMappingsPackage.IENTITY__SPECIFIED_SECONDARY_TABLES :
					return JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_SECONDARY_TABLES;
				case JpaCoreMappingsPackage.IENTITY__PRIMARY_KEY_JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_ENTITY__PRIMARY_KEY_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IENTITY__INHERITANCE_STRATEGY :
					return JpaJavaMappingsPackage.JAVA_ENTITY__INHERITANCE_STRATEGY;
				case JpaCoreMappingsPackage.IENTITY__DEFAULT_DISCRIMINATOR_VALUE :
					return JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_DISCRIMINATOR_VALUE;
				case JpaCoreMappingsPackage.IENTITY__SPECIFIED_DISCRIMINATOR_VALUE :
					return JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_DISCRIMINATOR_VALUE;
				case JpaCoreMappingsPackage.IENTITY__DISCRIMINATOR_VALUE :
					return JpaJavaMappingsPackage.JAVA_ENTITY__DISCRIMINATOR_VALUE;
				case JpaCoreMappingsPackage.IENTITY__DISCRIMINATOR_COLUMN :
					return JpaJavaMappingsPackage.JAVA_ENTITY__DISCRIMINATOR_COLUMN;
				case JpaCoreMappingsPackage.IENTITY__SEQUENCE_GENERATOR :
					return JpaJavaMappingsPackage.JAVA_ENTITY__SEQUENCE_GENERATOR;
				case JpaCoreMappingsPackage.IENTITY__TABLE_GENERATOR :
					return JpaJavaMappingsPackage.JAVA_ENTITY__TABLE_GENERATOR;
				case JpaCoreMappingsPackage.IENTITY__ATTRIBUTE_OVERRIDES :
					return JpaJavaMappingsPackage.JAVA_ENTITY__ATTRIBUTE_OVERRIDES;
				case JpaCoreMappingsPackage.IENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES :
					return JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES;
				case JpaCoreMappingsPackage.IENTITY__DEFAULT_ATTRIBUTE_OVERRIDES :
					return JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ATTRIBUTE_OVERRIDES;
				case JpaCoreMappingsPackage.IENTITY__ASSOCIATION_OVERRIDES :
					return JpaJavaMappingsPackage.JAVA_ENTITY__ASSOCIATION_OVERRIDES;
				case JpaCoreMappingsPackage.IENTITY__SPECIFIED_ASSOCIATION_OVERRIDES :
					return JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ASSOCIATION_OVERRIDES;
				case JpaCoreMappingsPackage.IENTITY__DEFAULT_ASSOCIATION_OVERRIDES :
					return JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ASSOCIATION_OVERRIDES;
				case JpaCoreMappingsPackage.IENTITY__NAMED_QUERIES :
					return JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_QUERIES;
				case JpaCoreMappingsPackage.IENTITY__NAMED_NATIVE_QUERIES :
					return JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_NATIVE_QUERIES;
				case JpaCoreMappingsPackage.IENTITY__ID_CLASS :
					return JpaJavaMappingsPackage.JAVA_ENTITY__ID_CLASS;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (specifiedName: ");
		result.append(specifiedName);
		result.append(", defaultName: ");
		result.append(defaultName);
		result.append(", inheritanceStrategy: ");
		result.append(inheritanceStrategy);
		result.append(", defaultDiscriminatorValue: ");
		result.append(defaultDiscriminatorValue);
		result.append(", specifiedDiscriminatorValue: ");
		result.append(specifiedDiscriminatorValue);
		result.append(", idClass: ");
		result.append(idClass);
		result.append(')');
		return result.toString();
	}

	public String getKey() {
		return IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	@Override
	public String getTableName() {
		return getTable().getName();
	}

	@Override
	public Table primaryDbTable() {
		return getTable().dbTable();
	}

	@Override
	public Table dbTable(String tableName) {
		for (Iterator<ITable> stream = this.associatedTablesIncludingInherited(); stream.hasNext();) {
			Table dbTable = stream.next().dbTable();
			if (dbTable != null && dbTable.matchesShortJavaClassName(tableName)) {
				return dbTable;
			}
		}
		return null;
	}

	@Override
	public Schema dbSchema() {
		return getTable().dbSchema();
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		this.setSpecifiedName(this.getType().annotationElementValue(NAME_ADAPTER, astRoot));
		this.setDefaultName(this.getType().getName());
		this.getJavaTable().updateFromJava(astRoot);
		this.updateSecondaryTablesFromJava(astRoot);
		this.updateNamedQueriesFromJava(astRoot);
		this.updateNamedNativeQueriesFromJava(astRoot);
		this.updateSpecifiedPrimaryKeyJoinColumnsFromJava(astRoot);
		this.updateAttributeOverridesFromJava(astRoot);
		this.updateAssociationOverridesFromJava(astRoot);
		this.setInheritanceStrategy(InheritanceType.fromJavaAnnotationValue(this.inheritanceStrategyAdapter.getValue(astRoot)));
		this.getJavaDiscriminatorColumn().updateFromJava(astRoot);
		this.setSpecifiedDiscriminatorValue(this.discriminatorValueAdapter.getValue(astRoot));
		this.setDefaultDiscriminatorValue(this.javaDefaultDiscriminatorValue());
		this.updateTableGeneratorFromJava(astRoot);
		this.updateSequenceGeneratorFromJava(astRoot);
		this.updateIdClassFromJava(astRoot);
	}

	private void updateIdClassFromJava(CompilationUnit astRoot) {
		if (this.idClassAnnotationAdapter.getAnnotation(astRoot) == null) {
			this.setIdClass(null);
		}
		else {
			this.setIdClass(this.idClassValueAdapter.getValue(astRoot));
		}
	}

	private JavaTable getJavaTable() {
		return (JavaTable) this.table;
	}

	private JavaDiscriminatorColumn getJavaDiscriminatorColumn() {
		return (JavaDiscriminatorColumn) this.discriminatorColumn;
	}

	private void updateTableGeneratorFromJava(CompilationUnit astRoot) {
		if (this.tableGeneratorAnnotationAdapter.getAnnotation(astRoot) == null) {
			if (this.tableGenerator != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (this.tableGenerator == null) {
				setTableGenerator(createTableGenerator());
			}
			this.getJavaTableGenerator().updateFromJava(astRoot);
		}
	}

	private JavaTableGenerator getJavaTableGenerator() {
		return (JavaTableGenerator) this.tableGenerator;
	}

	private void updateSequenceGeneratorFromJava(CompilationUnit astRoot) {
		if (this.sequenceGeneratorAnnotationAdapter.getAnnotation(astRoot) == null) {
			if (this.sequenceGenerator != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (this.sequenceGenerator == null) {
				setSequenceGenerator(createSequenceGenerator());
			}
			this.getJavaSequenceGenerator().updateFromJava(astRoot);
		}
	}

	private JavaSequenceGenerator getJavaSequenceGenerator() {
		return (JavaSequenceGenerator) this.sequenceGenerator;
	}

	/**
	 * From the Spec:
	 * If the DiscriminatorValue annotation is not specified, a
	 * provider-specific function to generate a value representing
	 * the entity type is used for the value of the discriminator
	 * column. If the DiscriminatorType is STRING, the discriminator
	 * value default is the entity name.
	 * 
	 * TODO extension point for provider-specific function?
	 */
	private String javaDefaultDiscriminatorValue() {
		if (this.getType().isAbstract()) {
			return null;
		}
		if (!this.discriminatorType().isString()) {
			return null;
		}
		return this.getName();
	}

	private DiscriminatorType discriminatorType() {
		return this.getDiscriminatorColumn().getDiscriminatorType();
	}

	/**
	 * here we just worry about getting the attribute override lists the same size;
	 * then we delegate to the attribute overrides to synch themselves up
	 */
	private void updateAttributeOverridesFromJava(CompilationUnit astRoot) {
		// synchronize the model attribute overrides with the Java source
		List<IAttributeOverride> attributeOverrides = getSpecifiedAttributeOverrides();
		int persSize = attributeOverrides.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			JavaAttributeOverride attributeOverride = (JavaAttributeOverride) attributeOverrides.get(i);
			if (attributeOverride.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			attributeOverride.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model attribute overrides beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				attributeOverrides.remove(persSize);
			}
		}
		else {
			// add new model attribute overrides until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				JavaAttributeOverride attributeOverride = this.createJavaAttributeOverride(javaSize);
				if (attributeOverride.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					getSpecifiedAttributeOverrides().add(attributeOverride);
					attributeOverride.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}
	
	/**
	 * here we just worry about getting the attribute override lists the same size;
	 * then we delegate to the attribute overrides to synch themselves up
	 */
	private void updateAssociationOverridesFromJava(CompilationUnit astRoot) {
		// synchronize the model attribute overrides with the Java source
		List<IAssociationOverride> associationOverrides = getSpecifiedAssociationOverrides();
		int persSize = associationOverrides.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			JavaAssociationOverride associationOverride = (JavaAssociationOverride) associationOverrides.get(i);
			if (associationOverride.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			associationOverride.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model attribute overrides beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				associationOverrides.remove(persSize);
			}
		}
		else {
			// add new model attribute overrides until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				JavaAssociationOverride associationOverride = this.createJavaAssociationOverride(javaSize);
				if (associationOverride.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					getSpecifiedAssociationOverrides().add(associationOverride);
					associationOverride.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}

	/**
	 * here we just worry about getting the secondary table lists the same size;
	 * then we delegate to the secondary tables to synch themselves up
	 */
	private void updateSecondaryTablesFromJava(CompilationUnit astRoot) {
		// synchronize the model secondary tables with the Java source
		List<ISecondaryTable> sTables = this.getSecondaryTables();
		int persSize = sTables.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			JavaSecondaryTable secondaryTable = (JavaSecondaryTable) sTables.get(i);
			if (secondaryTable.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			secondaryTable.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model secondary tables beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				sTables.remove(persSize);
			}
		}
		else {
			// add new model join columns until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				JavaSecondaryTable secondaryTable = this.createJavaSecondaryTable(javaSize);
				if (secondaryTable.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					getSecondaryTables().add(secondaryTable);
					secondaryTable.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}

	/**
	 * here we just worry about getting the named query lists the same size;
	 * then we delegate to the named queries to synch themselves up
	 */
	private void updateNamedQueriesFromJava(CompilationUnit astRoot) {
		// synchronize the model named queries with the Java source
		List<INamedQuery> queries = this.getNamedQueries();
		int persSize = queries.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			JavaNamedQuery namedQuery = (JavaNamedQuery) queries.get(i);
			if (namedQuery.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			namedQuery.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model named queries beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				queries.remove(persSize);
			}
		}
		else {
			// add new model join columns until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				JavaNamedQuery javaNamedQuery = this.createJavaNamedQuery(javaSize);
				if (javaNamedQuery.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					getNamedQueries().add(javaNamedQuery);
					javaNamedQuery.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}

	/**
	 * here we just worry about getting the named native query lists the same size;
	 * then we delegate to the named native queries to synch themselves up
	 */
	private void updateNamedNativeQueriesFromJava(CompilationUnit astRoot) {
		// synchronize the model named queries with the Java source
		List<INamedNativeQuery> queries = this.getNamedNativeQueries();
		int persSize = queries.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			JavaNamedNativeQuery namedQuery = (JavaNamedNativeQuery) queries.get(i);
			if (namedQuery.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			namedQuery.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model named queries beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				queries.remove(persSize);
			}
		}
		else {
			// add new model join columns until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				JavaNamedNativeQuery javaNamedQuery = this.createJavaNamedNativeQuery(javaSize);
				if (javaNamedQuery.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					getNamedNativeQueries().add(javaNamedQuery);
					javaNamedQuery.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}

	/**
	 * here we just worry about getting the primary key join column lists
	 * the same size; then we delegate to the join columns to synch
	 * themselves up
	 */
	private void updateSpecifiedPrimaryKeyJoinColumnsFromJava(CompilationUnit astRoot) {
		// synchronize the model primary key join columns with the Java source
		List<IPrimaryKeyJoinColumn> pkJoinColumns = getSpecifiedPrimaryKeyJoinColumns();
		int persSize = pkJoinColumns.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			JavaPrimaryKeyJoinColumn pkJoinColumn = (JavaPrimaryKeyJoinColumn) pkJoinColumns.get(i);
			if (pkJoinColumn.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			pkJoinColumn.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model primary key join columns beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				pkJoinColumns.remove(persSize);
			}
		}
		else {
			// add new model join columns until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				JavaPrimaryKeyJoinColumn jpkjc = this.createJavaPrimaryKeyJoinColumn(javaSize);
				if (jpkjc.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					getSpecifiedPrimaryKeyJoinColumns().add(jpkjc);
					jpkjc.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}

	public String primaryKeyColumnName() {
		String pkColumnName = null;
		for (Iterator<IPersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext();) {
			IPersistentAttribute attribute = stream.next();
			String name = attribute.primaryKeyColumnName();
			if (pkColumnName == null) {
				pkColumnName = name;
			}
			else if (name != null) {
				// if we encounter a composite primary key, return null
				return null;
			}
		}
		// if we encounter only a single primary key column name, return it
		return pkColumnName;
	}

	public String primaryKeyAttributeName() {
		String pkColumnName = null;
		String pkAttributeName = null;
		for (Iterator<IPersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext();) {
			IPersistentAttribute attribute = stream.next();
			String name = attribute.primaryKeyColumnName();
			if (pkColumnName == null) {
				pkColumnName = name;
				pkAttributeName = attribute.getName();
			}
			else if (name != null) {
				// if we encounter a composite primary key, return null
				return null;
			}
		}
		// if we encounter only a single primary key column name, return it
		return pkAttributeName;
	}

	@Override
	public boolean tableNameIsInvalid(String tableName) {
		return !CollectionTools.contains(this.associatedTableNamesIncludingInherited(), tableName);
	}

	@Override
	public Iterator<ITable> associatedTables() {
		return new CompositeIterator<ITable>(this.getTable(), this.getSecondaryTables().iterator());
	}

	@Override
	public Iterator<ITable> associatedTablesIncludingInherited() {
		return new CompositeIterator<ITable>(new TransformationIterator<ITypeMapping, Iterator<ITable>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<ITable> transform(ITypeMapping mapping) {
				return new FilteringIterator<ITable>(mapping.associatedTables()) {
					@Override
					protected boolean accept(Object o) {
						return true;
						//TODO
						//filtering these out so as to avoid the duplicate table, root and children share the same table
						//return !(o instanceof SingleTableInheritanceChildTableImpl);
					}
				};
			}
		});
	}

	@Override
	public Iterator<String> associatedTableNamesIncludingInherited() {
		return this.nonNullTableNames(this.associatedTablesIncludingInherited());
	}

	private Iterator<String> nonNullTableNames(Iterator<ITable> tables) {
		return new FilteringIterator<String>(this.tableNames(tables)) {
			@Override
			protected boolean accept(Object o) {
				return o != null;
			}
		};
	}

	private Iterator<String> tableNames(Iterator<ITable> tables) {
		return new TransformationIterator<ITable, String>(tables) {
			@Override
			protected String transform(ITable t) {
				return t.getName();
			}
		};
	}

	/**
	 * Return an iterator of Entities, each which inherits from the one before,
	 * and terminates at the root entity (or at the point of cyclicity).
	 */
	private Iterator<ITypeMapping> inheritanceHierarchy() {
		return new TransformationIterator<IPersistentType, ITypeMapping>(getPersistentType().inheritanceHierarchy()) {
			@Override
			protected ITypeMapping transform(IPersistentType type) {
				return type.getMapping();
			}
		};
		//TODO once we support inheritance, which of these should we use??
		//return this.getInheritance().typeMappingLineage();
	}

	public Iterator<String> allOverridableAttributeNames() {
		return new CompositeIterator<String>(new TransformationIterator<ITypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<String> transform(ITypeMapping mapping) {
				return mapping.overridableAttributeNames();
			}
		});
	}

	public Iterator<String> allOverridableAssociationNames() {
		return new CompositeIterator<String>(new TransformationIterator<ITypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<String> transform(ITypeMapping mapping) {
				return mapping.overridableAssociationNames();
			}
		});
	}

	public IAttributeOverride createAttributeOverride(int index) {
		return createJavaAttributeOverride(index);
	}

	private JavaAttributeOverride createJavaAttributeOverride(int index) {
		return JavaAttributeOverride.createAttributeOverride(new AttributeOverrideOwner(this), this.getType(), index);
	}

	public IAssociationOverride createAssociationOverride(int index) {
		return createJavaAssociationOverride(index);
	}

	private JavaAssociationOverride createJavaAssociationOverride(int index) {
		return JavaAssociationOverride.createAssociationOverride(new AssociationOverrideOwner(this), this.getType(), index);
	}

	public JavaSecondaryTable createSecondaryTable(int index) {
		return createJavaSecondaryTable(index);
	}

	private JavaSecondaryTable createJavaSecondaryTable(int index) {
		return JavaSecondaryTable.createJavaSecondaryTable(buildSecondaryTableOwner(), this.getType(), index);
	}

	private ITable.Owner buildSecondaryTableOwner() {
		return new ITable.Owner() {
			public ITextRange validationTextRange() {
				return JavaEntity.this.validationTextRange();
			}

			public ITypeMapping getTypeMapping() {
				return JavaEntity.this;
			}
		};
	}

	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
		return !this.getSpecifiedPrimaryKeyJoinColumns().isEmpty();
	}

	public IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index) {
		return this.createJavaPrimaryKeyJoinColumn(index);
	}

	private JavaPrimaryKeyJoinColumn createJavaPrimaryKeyJoinColumn(int index) {
		return JavaPrimaryKeyJoinColumn.createEntityPrimaryKeyJoinColumn(buildPkJoinColumnOwner(), this.getType(), index);
	}

	protected IAbstractJoinColumn.Owner buildPkJoinColumnOwner() {
		return new IEntity.PrimaryKeyJoinColumnOwner(this);
	}

	public JavaNamedQuery createNamedQuery(int index) {
		return createJavaNamedQuery(index);
	}

	private JavaNamedQuery createJavaNamedQuery(int index) {
		return JavaNamedQuery.createJavaNamedQuery(this.getType(), index);
	}

	public JavaNamedNativeQuery createNamedNativeQuery(int index) {
		return createJavaNamedNativeQuery(index);
	}

	private JavaNamedNativeQuery createJavaNamedNativeQuery(int index) {
		return JavaNamedNativeQuery.createJavaNamedNativeQuery(this.getType(), index);
	}

	public ISequenceGenerator createSequenceGenerator() {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaSequenceGenerator(getType());
	}

	public ITableGenerator createTableGenerator() {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaTableGenerator(getType());
	}

	// ********** misc **********
	private static void attributeChanged(Object value, AnnotationAdapter annotationAdapter) {
		Annotation annotation = annotationAdapter.getAnnotation();
		if (value == null) {
			if (annotation != null) {
				annotationAdapter.removeAnnotation();
			}
		}
		else {
			if (annotation == null) {
				annotationAdapter.newMarkerAnnotation();
			}
		}
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getJavaTable().candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (ISecondaryTable sTable : this.getSecondaryTables()) {
			result = ((JavaSecondaryTable) sTable).connectedCandidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		for (IPrimaryKeyJoinColumn column : this.getPrimaryKeyJoinColumns()) {
			result = ((JavaPrimaryKeyJoinColumn) column).connectedCandidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		for (IAttributeOverride override : this.getAttributeOverrides()) {
			result = ((JavaAttributeOverride) override).connectedCandidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		for (IAssociationOverride override : this.getAssociationOverrides()) {
			result = ((JavaAssociationOverride) override).connectedCandidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		result = this.getJavaDiscriminatorColumn().candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		JavaTableGenerator jtg = this.getJavaTableGenerator();
		if (jtg != null) {
			result = jtg.candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		JavaSequenceGenerator jsg = this.getJavaSequenceGenerator();
		if (jsg != null) {
			result = jsg.candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	// ********** static methods **********
	protected static DeclarationAnnotationElementAdapter<String> buildStrategyAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(INHERITANCE_ANNOTATION_ADAPTER, JPA.INHERITANCE__STRATEGY);
	}

	private static DeclarationAnnotationElementAdapter<String> buildDiscriminatorValueAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DISCRIMINATOR_ANNOTATION_ADAPTER, JPA.DISCRIMINATOR_VALUE__VALUE);
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildIdClassValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String, TypeLiteral>(ID_CLASS_ADAPTER, JPA.ID_CLASS__VALUE, false, SimpleTypeStringExpressionConverter.instance());
	}
}
