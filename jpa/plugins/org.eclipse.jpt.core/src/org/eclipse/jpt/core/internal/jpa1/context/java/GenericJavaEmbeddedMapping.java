/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumn.Owner;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaBaseEmbeddedMapping;
import org.eclipse.jpt.core.internal.jpa1.context.AssociationOverrideInverseJoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.AssociationOverrideJoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.AssociationOverrideJoinTableValidator;
import org.eclipse.jpt.core.internal.jpa1.context.AssociationOverrideValidator;
import org.eclipse.jpt.core.internal.jpa1.context.EmbeddableOverrideDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.JoinTableTableDescriptionProvider;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaEmbeddedMapping
	extends AbstractJavaBaseEmbeddedMapping<EmbeddedAnnotation>
	implements JavaEmbeddedMapping2_0
{
	protected final JavaAssociationOverrideContainer associationOverrideContainer;

	public GenericJavaEmbeddedMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.associationOverrideContainer = this.buildAssociationOverrideContainer();
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.associationOverrideContainer.initialize(this.getResourcePersistentAttribute());
	}
	
	@Override
	protected void update() {
		super.update();
		this.associationOverrideContainer.update(this.getResourcePersistentAttribute());
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		this.associationOverrideContainer.postUpdate();
	}
	
	//****************** JavaAttributeMapping implementation *******************

	public String getKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return EmbeddedAnnotation.ANNOTATION_NAME;
	}
	
	//only putting this in EmbeddedMapping since relationship mappings
	//defined within an embedded id class are not supported by the 2.0 spec.
	//i.e. the mappedBy choices will not include attributes nested in an embedded mapping
	@Override
	public Iterator<String> allMappingNames() {
		return this.isJpa2_0Compatible() ?
				new CompositeIterator<String>(this.getName(), this.allEmbeddableAttributeMappingNames()) :
				super.allMappingNames();
	}
	
	protected Iterator<String> allEmbeddableAttributeMappingNames() {
		return this.embeddableOverrideableMappingNames(
			new Transformer<AttributeMapping, Iterator<String>>() {
				public Iterator<String> transform(AttributeMapping mapping) {
					return mapping.allMappingNames();
				}
			}
		);
	}

	@Override
	public AttributeMapping resolveAttributeMapping(String name) {
		AttributeMapping resolvedMapping = super.resolveAttributeMapping(name);
		if (resolvedMapping != null) {
			return resolvedMapping;
		}
		if (this.isJpa2_0Compatible()) {
			int dotIndex = name.indexOf('.');
			if (dotIndex != -1) {
				if (getName().equals(name.substring(0, dotIndex))) {
					for (AttributeMapping attributeMapping : CollectionTools.iterable(embeddableAttributeMappings())) {
						resolvedMapping = attributeMapping.resolveAttributeMapping(name.substring(dotIndex + 1));
						if (resolvedMapping != null) {
							return resolvedMapping;
						}
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public RelationshipReference resolveRelationshipReference(String attributeName) {
		if (this.isJpa2_0Compatible()) {
			int dotIndex = attributeName.indexOf('.');
			if (dotIndex != -1) {
				if (getName().equals(attributeName.substring(0, dotIndex))) {
					attributeName = attributeName.substring(dotIndex + 1);
					AssociationOverride override = getAssociationOverrideContainer().getAssociationOverrideNamed(attributeName);
					if (override != null && !override.isVirtual()) {
						return override.getRelationshipReference();
					}
					if (this.getTargetEmbeddable() == null) {
						return null;
					}
					return this.getTargetEmbeddable().resolveRelationshipReference(attributeName);
				}
			}
		}
		return null;
	}
	
	//****************** AbstractJavaAttributeMapping implementation *******************
	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		if (this.isJpa2_0Compatible()) {
			names.add(JPA.ASSOCIATION_OVERRIDE);
			names.add(JPA.ASSOCIATION_OVERRIDES);
		}
	}
	
	
	//****************** association overrides - 2.0 supports this *******************
	
	protected JavaAssociationOverrideContainer buildAssociationOverrideContainer() {
		return this.isJpa2_0Compatible() ? 
			((JpaFactory2_0) this.getJpaFactory()).buildJavaAssociationOverrideContainer(this, new AssociationOverrideContainerOwner()) : 
			new NullJavaAssociationOverrideContainer(this);
	}

	public JavaAssociationOverrideContainer getAssociationOverrideContainer() {
		return this.associationOverrideContainer;
	}

	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		
		result = getAssociationOverrideContainer().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}


	// ********** validation **********

	@Override
	protected void validateOverrides(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validateOverrides(messages, reporter, astRoot);
		this.getAssociationOverrideContainer().validate(messages, reporter, astRoot);
	}


	// ********** association override container owner **********

	protected class AssociationOverrideContainerOwner
		implements JavaAssociationOverrideContainer.Owner
	{	
		public TypeMapping getTypeMapping() {
			return GenericJavaEmbeddedMapping.this.getTypeMapping();
		}
		
		public TypeMapping getOverridableTypeMapping() {
			return GenericJavaEmbeddedMapping.this.getOverridableTypeMapping();
		}
		
		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = getOverridableTypeMapping();
			return (typeMapping == null) ? 
					EmptyIterator.<String>instance()
					: typeMapping.allOverridableAssociationNames();
		}
		
		public RelationshipReference resolveRelationshipReference(String associationOverrideName) {
			return MappingTools.resolveRelationshipReference(getOverridableTypeMapping(), associationOverrideName);
		}
		
		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}
		
		public Iterator<String> candidateTableNames() {
			return getTypeMapping().associatedTableNamesIncludingInherited();
		}
		
		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return getTypeMapping().getDbTable(tableName);
		}
		
		public String getDefaultTableName() {
			return getTypeMapping().getPrimaryTableName();
		}
		
		public String getPossiblePrefix() {
			return null;
		}
		
		public String getWritePrefix() {
			return null;
		}
		
		//no prefix, so always true
		public boolean isRelevant(String overrideName) {
			return true;
		}
		
		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaEmbeddedMapping.this.getValidationTextRange(astRoot);
		}

		public JptValidator buildValidator(BaseOverride override, BaseOverride.Owner owner, OverrideTextRangeResolver textRangeResolver) {
			return new AssociationOverrideValidator((AssociationOverride) override, (AssociationOverride.Owner) owner, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}
		
		public JptValidator buildColumnValidator(BaseOverride override, BaseColumn column, BaseColumn.Owner owner, BaseColumnTextRangeResolver textRangeResolver) {
			return new AssociationOverrideJoinColumnValidator((AssociationOverride) override, (JoinColumn) column, (JoinColumn.Owner) owner, (JoinColumnTextRangeResolver) textRangeResolver, new EntityTableDescriptionProvider());
		}

		public JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
			return new AssociationOverrideJoinColumnValidator(override, column, owner, textRangeResolver, new JoinTableTableDescriptionProvider());
		}

		public JptValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
			return new AssociationOverrideInverseJoinColumnValidator(override, column, owner, textRangeResolver, new JoinTableTableDescriptionProvider());
		}

		public JptValidator buildTableValidator(AssociationOverride override, Table table, TableTextRangeResolver textRangeResolver) {
			return new AssociationOverrideJoinTableValidator(override, (JoinTable) table, textRangeResolver);
		}
	}
}
