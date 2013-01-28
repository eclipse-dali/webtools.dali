/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceNode;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.TypeDeclarationTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.ColumnMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.ReferenceTable;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Gather some of the behavior common to the Java and XML models. :-(
 */
public final class MappingTools {

	/**
	 * Return whether the specified type is "basic".
	 * @param fullyQualifiedName may include array brackets but not generic type arguments
	 */
	public static boolean typeIsBasic(IJavaProject javaProject, String fullyQualifiedName) {
		if (fullyQualifiedName == null) {
			return false;
		}

		int arrayDepth = TypeDeclarationTools.arrayDepth(fullyQualifiedName);
		if (arrayDepth > 1) {
			return false;  // multi-dimensional arrays are not supported
		}

		if (arrayDepth == 1) {
			String elementTypeName = TypeDeclarationTools.elementTypeName(fullyQualifiedName, 1);
			return elementTypeIsValidForBasicArray(elementTypeName);
		}

		// arrayDepth == 0
		if (ClassNameTools.isVariablePrimitive(fullyQualifiedName)) {
			return true;  // any primitive but 'void'
		}
		if (ClassNameTools.isVariablePrimitiveWrapper(fullyQualifiedName)) {
			return true;  // any primitive wrapper but 'java.lang.Void'
		}
		if (typeIsOtherValidBasicType(fullyQualifiedName)) {
			return true;
		}
		IType type = JDTTools.findType(javaProject, fullyQualifiedName);
		if (type == null) {
			return false;
		}
		if (JDTTools.typeIsSerializable(javaProject, type)) {
			return true;
		}
		if (JDTTools.typeIsEnum(type)) {
			return true;
		}
		return false;
	}

	/**
	 * Return whether the specified type is a valid element type for
	 * a one-dimensional array that can default to a basic mapping:<ul>
	 * <li><code>byte</code>
	 * <li><code>java.lang.Byte</code>
	 * <li><code>char</code>
	 * <li><code>java.lang.Character</code>
	 * </ul>
	 */
	public static boolean elementTypeIsValidForBasicArray(String elementTypeName) {
		return ArrayTools.contains(VALID_BASIC_ARRAY_ELEMENT_TYPE_NAMES, elementTypeName);
	}

	private static final String[] VALID_BASIC_ARRAY_ELEMENT_TYPE_NAMES = {
		byte.class.getName(),
		char.class.getName(),
		java.lang.Byte.class.getName(),
		java.lang.Character.class.getName()
	};

	private static final String[] BASIC_ARRAY_TYPE_NAMES = {
		byte[].class.getSimpleName(),
		Byte[].class.getSimpleName(),
		char[].class.getSimpleName(),
		Character[].class.getSimpleName()
	};
	
	/**
	 * Return whether the specified type is among the various "other" types
	 * that can default to a basic mapping.
	 */
	public static boolean typeIsOtherValidBasicType(String typeName) {
		return ArrayTools.contains(OTHER_VALID_BASIC_TYPE_NAMES, typeName);
	}
	
	private static final String[] OTHER_VALID_BASIC_TYPE_NAMES = {
		java.lang.String.class.getName(),
		java.math.BigInteger.class.getName(),
		java.math.BigDecimal.class.getName(),
		java.util.Date.class.getName(),
		java.util.Calendar.class.getName(),
		java.sql.Date.class.getName(),
		java.sql.Time.class.getName(),
		java.sql.Timestamp.class.getName(),
	};
	
	private static final String[] PRIMITIVE_TYPE_NAMES = {
		boolean.class.getName(),
		byte.class.getName(),
		char.class.getName(),
		double.class.getName(),
		float.class.getName(),
		int.class.getName(),
		long.class.getName(),
		short.class.getName()
	};
	
	private static final String[] PRIMITIVE_WRAPPER_TYPE_NAMES = {
		Boolean.class.getName(),
		Byte.class.getName(),
		Character.class.getName(),
		Double.class.getName(),
		Float.class.getName(),
		Integer.class.getName(),
		Long.class.getName(),
		Short.class.getName()
	};
	
	private static final String[] COLLECTION_TYPE_NAMES = {
		Collection.class.getName(),
		List.class.getName(),
		Map.class.getName(),
		Set.class.getName()
	};

	/**
	 * Default join table name from the JPA spec:<br>
	 * 	"The concatenated names of the two associated primary
	 * 	entity tables, separated by a underscore."
	 * <pre>
	 * [owning table name]_[target table name]
	 * </pre>
	 * <strong>NB:</strong> The <em>names</em> are concatenated,
	 * <em>not</em> the <em>identifiers</em>.
	 * E.g. the join table for <code>"Foo"</code> and <code>"baR"</code>
	 * (where the "delimited" identifier is required) is:
	 * <pre>
	 *     "Foo_baR"
	 * </pre>
	 * not
	 * <pre>
	 *     "Foo"_"baR"
	 * </pre>
	 * As a result, we cannot honestly calculate the default name without a
	 * database connection. We need the database to convert the resulting name
	 * to an identifier appropriate to the current database.
	 */
	public static String buildJoinTableDefaultName(ReadOnlyRelationship relationship) {
		if (relationship.getJpaProject().getDataSource().connectionProfileIsActive()) {
			return buildDbJoinTableDefaultName(relationship);
		}
		// continue with a "best effort":
		String owningTableName = relationship.getTypeMapping().getPrimaryTableName();
		if (owningTableName == null) {
			return null;
		}
		RelationshipMapping relationshipMapping = relationship.getMapping();
		if (relationshipMapping == null) {
			return null;
		}
		Entity targetEntity = relationshipMapping.getResolvedTargetEntity();
		if (targetEntity == null) {
			return null;
		}
		String targetTableName = targetEntity.getPrimaryTableName();
		if (targetTableName == null) {
			return null;
		}
		return owningTableName + '_' + targetTableName;
	}

	/**
	 * Use the database to build a more accurate default name.
	 */
	private static String buildDbJoinTableDefaultName(ReadOnlyRelationship relationship) {
		Table owningTable = relationship.getTypeMapping().getPrimaryDbTable();
		if (owningTable == null) {
			return null;
		}
		RelationshipMapping relationshipMapping = relationship.getMapping();
		if (relationshipMapping == null) {
			return null;
		}
		Entity targetEntity = relationshipMapping.getResolvedTargetEntity();
		if (targetEntity == null) {
			return null;
		}
		Table targetTable = targetEntity.getPrimaryDbTable();
		if (targetTable == null) {
			return null;
		}
		String name = owningTable.getName() + '_' + targetTable.getName();
		return owningTable.getDatabase().convertNameToIdentifier(name);
	}

	/**
	 * Default collection table name from the JPA spec:<br>
	 * 	"The concatenation of the name of the containing entity and
	 *  the name of the collection attribute, separated by an underscore."
	 * <pre>
	 * [owning entity name]_[attribute name]
	 * </pre>
	 */
	public static String buildCollectionTableDefaultName(ElementCollectionMapping2_0 mapping) {
		Entity entity = mapping.getEntity();
		if (entity == null) {
			return null;
		}
		String owningEntityName = entity.getName();
		String attributeName = mapping.getName();
		return owningEntityName + '_' + attributeName;
	}

	/**
	 * Return the join column's default name;
	 * which is typically<pre>
	 *     [attribute name]_[referenced column name]
	 * </pre>
	 * But, if we don't have an attribute name (e.g. in a unidirectional
	 * one-to-many or many-to-many) is<pre>
	 *     [target entity name]_[referenced column name]
	 * </pre>
	 * @see #buildJoinTableDefaultName(ReadOnlyRelationship)
	 */
	public static String buildJoinColumnDefaultName(ReadOnlyJoinColumn joinColumn, ReadOnlyJoinColumn.Owner owner) {
		if (owner.getJoinColumnsSize() != 1) {
			return null;
		}
		String prefix = owner.getAttributeName();
		if (prefix == null) {
			Entity targetEntity = owner.getRelationshipTarget();
			if (targetEntity == null) {
				return null;
			}
			prefix = targetEntity.getName();
		}
		// It's not clear which of these is correct....
		// The spec implies that the referenced column is always the
		// *primary key* column of the target entity; i.e. the primary key as
		// defined on the *database* (or, possibly, the primary key as defined
		// by the target entity's Id mapping?), not the name of the referenced
		// column irrespective of whether it is the primary key on the target
		// entity's table. But this seems like it would be wrong; since the
		// referenced column need not be a primary key and we don't always have
		// access to the database.
		//
		// Column targetColumn = joinColumn.getTargetPrimaryKeyDbColumn();
		String targetColumnName = joinColumn.getReferencedColumnName();
		if (targetColumnName == null) {
			return null;
		}
		String name = prefix + '_' + targetColumnName;
		// Again, it's not clear which of these is correct....
		// Converting the name to an identifier will result in the identifier
		// being delimited nearly every time (at least on non-Sybase/MS
		// databases); but that probably is not the intent of the spec....
		//
		// return targetColumn.getDatabase().convertNameToIdentifier(name);
		return name;
	}

	/**
	 * Return the name of the attribute in the specified mapping's target entity
	 * that is owned by the mapping.
	 */
	public static String getTargetAttributeName(RelationshipMapping relationshipMapping) {
		if (relationshipMapping == null) {
			return null;
		}
		Entity targetEntity = relationshipMapping.getResolvedTargetEntity();
		if (targetEntity == null) {
			return null;
		}
		for (ReadOnlyPersistentAttribute attribute : targetEntity.getPersistentType().getAllAttributes()) {
			if (attribute.getMapping().isOwnedBy(relationshipMapping)) {
				return attribute.getName();
			}
		}
		return null;
	}

	/**
	 * If appropriate, return the name of the single primary key column of the
	 * relationship target.
	 * Spec states:<br>
	 *     "The same name as the primary key column of the referenced table."<br>
	 * We are assuming that the primary key column is defined by the mappings instead of the database.
	 */
	public static String buildJoinColumnDefaultReferencedColumnName(ReadOnlyJoinColumn.Owner joinColumnOwner) {
		if (joinColumnOwner.getJoinColumnsSize() != 1) {
			return null;
		}
		Entity targetEntity = joinColumnOwner.getRelationshipTarget();
		if (targetEntity == null) {
			return null;
		}
		return targetEntity.getPrimaryKeyColumnName();
	}

	public static ColumnMapping getColumnMapping(String attributeName, PersistentType persistentType) {
		if ((attributeName == null) || (persistentType == null)) {
			return null;
		}
		for (ReadOnlyPersistentAttribute persAttribute : persistentType.getAllAttributes()) {
			if (attributeName.equals(persAttribute.getName())) {
				if (persAttribute.getMapping() instanceof ColumnMapping) {
					return (ColumnMapping) persAttribute.getMapping();
				}
				// keep looking or return null???
			}
		}
		return null;
	}

	public static RelationshipMapping getRelationshipMapping(String attributeName, TypeMapping typeMapping) {
		if ((attributeName == null) || (typeMapping == null)) {
			return null;
		}
		for (AttributeMapping attributeMapping : typeMapping.getAllAttributeMappings()) {
			if (attributeName.equals(attributeMapping.getName())) {
				if (attributeMapping instanceof RelationshipMapping) {
					return (RelationshipMapping) attributeMapping;
				}
				// keep looking or return null???
			}
		}
		return null;
	}

	public static void convertReferenceTableDefaultToSpecifiedJoinColumn(ReferenceTable referenceTable) {
		JoinColumn defaultJoinColumn = referenceTable.getDefaultJoinColumn();
		if (defaultJoinColumn != null) {
			String columnName = defaultJoinColumn.getDefaultName();
			String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();
			JoinColumn joinColumn = referenceTable.addSpecifiedJoinColumn();
			joinColumn.setSpecifiedName(columnName);
			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}

	public static void convertJoinTableDefaultToSpecifiedInverseJoinColumn(JoinTable joinTable) {
		JoinColumn defaultInverseJoinColumn = joinTable.getDefaultInverseJoinColumn();
		if (defaultInverseJoinColumn != null) {
			String columnName = defaultInverseJoinColumn.getDefaultName();
			String referencedColumnName = defaultInverseJoinColumn.getDefaultReferencedColumnName();
			JoinColumn joinColumn = joinTable.addSpecifiedInverseJoinColumn(0);
			joinColumn.setSpecifiedName(columnName);
			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}

	public static String getMetamodelFieldMapKeyTypeName(CollectionMapping2_0 mapping) {
		PersistentType targetType = mapping.getResolvedTargetType();
		String mapKey = mapping.getMapKey();
		if ((mapKey == null) || (targetType == null)) {
			String mapKeyClass = mapping.getFullyQualifiedMapKeyClass();
			return mapKeyClass != null ? mapKeyClass : MetamodelField.DEFAULT_TYPE_NAME;
		}
		ReadOnlyPersistentAttribute mapKeyAttribute = targetType.resolveAttribute(mapKey);
		if (mapKeyAttribute == null) {
			return MetamodelField.DEFAULT_TYPE_NAME;
		}
		AttributeMapping2_0 mapKeyMapping = (AttributeMapping2_0) mapKeyAttribute.getMapping();
		if (mapKeyMapping == null) {
			return MetamodelField.DEFAULT_TYPE_NAME;
		}
		return mapKeyMapping.getMetamodelTypeName();
	}

	// TODO move to TypeMapping? may need different name (or may need to rename existing #resolve...)
	public static Column resolveOverriddenColumn(TypeMapping overridableTypeMapping, String attributeName) {
		// convenience null check to simplify client code
		if (overridableTypeMapping == null) {
			return null;
		}

		for (TypeMapping typeMapping : overridableTypeMapping.getInheritanceHierarchy()) {
			Column column = typeMapping.resolveOverriddenColumn(attributeName);
			if (column != null) {
				return column;
			}
		}
		return null;
	}

	// TODO move to TypeMapping? may need different name (or may need to rename existing #resolve...)
	public static Relationship resolveOverriddenRelationship(TypeMapping overridableTypeMapping, String attributeName) {
		// convenience null check to simplify client code
		if (overridableTypeMapping == null) {
			return null;
		}

		for (TypeMapping typeMapping : overridableTypeMapping.getInheritanceHierarchy()) {
			Relationship relationship = typeMapping.resolveOverriddenRelationship(attributeName);
			if (relationship != null) {
				return relationship;
			}
		}
		return null;
	}

	public static String getPrimaryKeyColumnName(Entity entity) {
		String pkColumnName = null;
		for (ReadOnlyPersistentAttribute attribute : entity.getPersistentType().getAllAttributes()) {
			String current = attribute.getPrimaryKeyColumnName();
			if (current != null) {
				// 229423 - if the attribute is a primary key, but it has an attribute override,
				// use the override column instead
				ReadOnlyAttributeOverride attributeOverride = entity.getAttributeOverrideContainer().getOverrideNamed(attribute.getName());
				if (attributeOverride != null) {
					current = attributeOverride.getColumn().getName();
				}
			}
			if (pkColumnName == null) {
				pkColumnName = current;
			} else {
				if (current != null) {
					// if we encounter a composite primary key, return null
					return null;
				}
			}
		}
		// if we encounter only a single primary key column name, return it
		return pkColumnName;
	}

	/**
	 * "Unqualify" the specified attribute name, removing the mapping's name
	 * from the front of the attribute name if it is present. For example, if
	 * the mapping's name is <code>"foo"</code>, the attribute name
	 * <code>"foo.bar"</code> would be converted to <code>"bar"</code>).
	 * Return <code>null</code> if the attribute name cannot be "unqualified".
	 */
	public static String unqualify(String mappingName, String attributeName) {
		if (mappingName == null) {
			return null;
		}
		if ( ! attributeName.startsWith(mappingName)) {
			return null;
		}
		int mappingNameLength = mappingName.length();
		if (attributeName.length() <= mappingNameLength) {
			return null;
		}
		return (attributeName.charAt(mappingNameLength) == '.') ? attributeName.substring(mappingNameLength + 1) : null;
	}

	/**
	 * This transformer will prepend a specified qualifier, followed by a
	 * dot ('.'), to a string. For example, if a mapping's name is
	 * <code>"foo"</code> and one of its attribute's is named
	 * <code>"bar"</code>, the attribute's name will be transformed
	 * into <code>"foo.bar"</code>. If the specified qualifier is
	 * <code>null</code> (or an empty string), only a dot will be prepended
	 * to a string.
	 */
	public static class QualifierTransformer
		implements Transformer<String, String>
	{
		private final String prefix;
		public QualifierTransformer(String qualifier) {
			super();
			this.prefix = (qualifier == null) ? "." : qualifier + '.'; //$NON-NLS-1$
		}
		public String transform(String s) {
			return this.prefix + s;
		}
	}

	/**
	 * TODO temporary hack since we don't know yet where to put
	 * any messages for types in another project (e.g. referenced by
	 * persistence.xml)
	 */
	public static boolean nodeIsInternalSource(JpaContextNode contextNode, JavaResourceNode resourceNode) {
		IResource resource = contextNode.getResource();
		// 'resource' will be null if the node is "external" and binary;
		// the resource will be in a different project if the node is "external" and source;
		// the node will be binary if it is in a JAR in the current project
		return (resource != null) &&
				resource.getProject().equals(contextNode.getJpaProject().getProject()) &&
				(resourceNode instanceof SourceNode);
	}

	/**
	 * Returns sorted names of interfaces of the given project
	 */
	public static Iterable<String> getSortedJavaInterfaceNames(IJavaProject javaProject) {
		return IterableTools.sort(getJavaInterfaceNames(javaProject));
	}
	
	/**
	 * Returns the names of interfaces of the given project
	 */
	public static Iterable<String> getJavaInterfaceNames(IJavaProject javaProject) {
		return IterableTools.transform(getJavaInterfaces(javaProject), JDT_TYPE_NAME_TRANSFORMER);
	}	
	
	/**
	 * Returns all the interfaces across the given project
	 */
	public static Iterable<IType> getJavaInterfaces(IJavaProject javaProject) {
		return new FilteringIterable<IType>(getJavaTypes(javaProject)) {
			@Override
			protected boolean accept(IType type) {
				try {
					return type.isInterface();
				} catch (JavaModelException e) {
					JptJpaCorePlugin.instance().logError(e);
				}
				return false;
			}
		};
	}
	
	/**
	 * Returns sorted names of classes of the given project
	 */
	public static Iterable<String> getSortedJavaClassNames(IJavaProject javaProject) {
		return IterableTools.sort(getJavaClassNames(javaProject));
	}
	
	/**
	 * Returns the names of classes of the given project
	 */
	public static Iterable<String> getJavaClassNames(IJavaProject javaProject) {
		return IterableTools.transform(getJavaClasses(javaProject), JDT_TYPE_NAME_TRANSFORMER);
	}

	/**
	 * Returns all the classes across the given project
	 */
	public static Iterable<IType> getJavaClasses(IJavaProject javaProject) {
		return new FilteringIterable<IType>(getJavaTypes(javaProject)) {
			@Override
			protected boolean accept(IType type) {
				try {
					return type.isClass();
				} catch (JavaModelException e) {
					JptJpaCorePlugin.instance().logError(e);
				}
				return false;
			}
		};
	}
	
	/**
	 * Returns all the enums across the given project
	 */
	public static Iterable<IType> getJavaEnums(IJavaProject javaProject) {
		return new FilteringIterable<IType>(getJavaTypes(javaProject)) {
			@Override
			protected boolean accept(IType type) {
				try {
					return type.isEnum();
				} catch (JavaModelException e) {
					JptJpaCorePlugin.instance().logError(e);
				}
				return false;
			}
		};
	}
	
	/**
	 * Returns the names of enums in the given project
	 */
	public static Iterable<String> getJavaEnumNames(IJavaProject javaProject) {
		return IterableTools.transform(getJavaEnums(javaProject), JDT_TYPE_NAME_TRANSFORMER);
	}
	
	/**
	 * Returns sorted names of enums in the given project
	 */
	public static Iterable<String> getSortedJavaEnumNames(IJavaProject javaProject) {
		return IterableTools.sort(getJavaEnumNames(javaProject));
	}
	
	/**
	 * Returns all the types cross the given project
	 */
	public static Iterable<IType> getJavaTypes(IJavaProject javaProject) {
		List<IType> typesList = new ArrayList<IType>();
		try {
			IPackageFragmentRoot[] pkgRoots = javaProject.getAllPackageFragmentRoots();
			for (IPackageFragmentRoot root : pkgRoots) {
					IJavaElement[] jElements = root.getChildren();
					for (IJavaElement jElement : jElements) {
						if (jElement.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
							ICompilationUnit[] units = ((IPackageFragment) jElement).getCompilationUnits();
							for (ICompilationUnit unit : units) {
								CollectionTools.addAll(typesList, unit.getTypes());
							}
						}
					}
			}
		} catch (JavaModelException e) {
			JptJpaCorePlugin.instance().logError(e);
		}
		return typesList;
	}
	
	/**
	 * Returns the names of basic array types.
	 */
	public static Iterable<String> getBasicArrayTypeNames() {
		return ListTools.list(BASIC_ARRAY_TYPE_NAMES);
	}
	
	
	/**
	 * Returns the names of primary basic types with including primitives
	 */
	public static Iterable<String> getPrimaryBasicTypeNamesWithoutPrimitives() {
		List<String> names = new ArrayList<String>();
		CollectionTools.addAll(names, PRIMITIVE_WRAPPER_TYPE_NAMES);
		CollectionTools.addAll(names, OTHER_VALID_BASIC_TYPE_NAMES);
		return names;
	}
	
	/**
	 * Returns the names of primary basic types
	 */
	public static Iterable<String> getPrimaryBasicTypeNames() {
		List<String> names = new ArrayList<String>();
		CollectionTools.addAll(names, PRIMITIVE_TYPE_NAMES);
		CollectionTools.addAll(names, PRIMITIVE_WRAPPER_TYPE_NAMES);
		CollectionTools.addAll(names, OTHER_VALID_BASIC_TYPE_NAMES);
		return names;
	}
	
	/**
	 * Returns the names of all possible valid basic types
	 * 
	 * @return a String iterable that includes extra basic types besides 
	 * these ones returned by getPrimaryBasicTypeNames method
	 * 
	 * @see #getPrimaryBasicTypeNames()
	 */
	public static Iterable<String> getAllBasicTypeNames() {
		List<String> names = new ArrayList<String>();
		CollectionTools.addAll(names, getPrimaryBasicTypeNames());
		CollectionTools.addAll(names, BASIC_ARRAY_TYPE_NAMES);
		names.add(Enum.class.getSimpleName());
		return names;
	}
	
	/**
	 * Returns the names of collection types
	 */
	public static Iterable<String> getCollectionTypeNames() {
		List<String> names = new ArrayList<String>();
		CollectionTools.addAll(names, COLLECTION_TYPE_NAMES);
		return names;
	}

	public static final Transformer<IType, String> JDT_TYPE_NAME_TRANSFORMER = new JdtTypeNameTransformer();

	public static class JdtTypeNameTransformer
		extends TransformerAdapter<IType, String>
	{
		@Override
		public String transform(IType type) {
			return type.getFullyQualifiedName();
		}
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private MappingTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
