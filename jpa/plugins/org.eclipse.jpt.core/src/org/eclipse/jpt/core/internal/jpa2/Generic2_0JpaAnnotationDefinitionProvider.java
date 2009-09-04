/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2;

import java.util.List;
import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.AbstractJpaAnnotationDefintionProvider;
import org.eclipse.jpt.core.internal.jpa2.resource.java.Access2_0AnnotationDefinition;
import org.eclipse.jpt.core.internal.jpa2.resource.java.AssociationOverride2_0AnnotationDefinition;
import org.eclipse.jpt.core.internal.jpa2.resource.java.AssociationOverrides2_0AnnotationDefinition;
import org.eclipse.jpt.core.internal.jpa2.resource.java.SequenceGenerator2_0AnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverrideAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverridesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.BasicAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorValueAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EmbeddableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EmbeddedAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EmbeddedIdAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EntityAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EnumeratedAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValueAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.IdAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.IdClassAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.InheritanceAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JoinColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JoinColumnsAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JoinTableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.LobAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ManyToManyAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ManyToOneAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.MapKeyAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.MappedSuperclassAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQueriesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQueryAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedQueriesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedQueryAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.OneToManyAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.OneToOneAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.OrderByAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumnsAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTablesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TableGeneratorAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TemporalAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TransientAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.VersionAnnotationDefinition;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;

/**
 * Support for existing JPA 1.0 annotations, new JPA 2.0 annotations, and 
 * augmented support for annotations changed from 1.0 to 2.0
 */
public class Generic2_0JpaAnnotationDefinitionProvider
	extends AbstractJpaAnnotationDefintionProvider
{
	// singleton
	private static final JpaAnnotationDefinitionProvider INSTANCE = 
			new Generic2_0JpaAnnotationDefinitionProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaAnnotationDefinitionProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private Generic2_0JpaAnnotationDefinitionProvider() {
		super();
	}
	
	
	@Override
	protected void addTypeAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(Access2_0AnnotationDefinition.instance());
		definitions.add(AssociationOverride2_0AnnotationDefinition.instance());
		definitions.add(AssociationOverrides2_0AnnotationDefinition.instance());
		definitions.add(AttributeOverrideAnnotationDefinition.instance());
		definitions.add(AttributeOverridesAnnotationDefinition.instance());
		definitions.add(DiscriminatorColumnAnnotationDefinition.instance());
		definitions.add(DiscriminatorValueAnnotationDefinition.instance());
		definitions.add(EmbeddableAnnotationDefinition.instance());
		definitions.add(EntityAnnotationDefinition.instance());
		definitions.add(IdClassAnnotationDefinition.instance());
		definitions.add(InheritanceAnnotationDefinition.instance());
		definitions.add(MappedSuperclassAnnotationDefinition.instance());
		definitions.add(NamedQueryAnnotationDefinition.instance());
		definitions.add(NamedQueriesAnnotationDefinition.instance());
		definitions.add(NamedNativeQueryAnnotationDefinition.instance());
		definitions.add(NamedNativeQueriesAnnotationDefinition.instance());
		definitions.add(PrimaryKeyJoinColumnAnnotationDefinition.instance());
		definitions.add(PrimaryKeyJoinColumnsAnnotationDefinition.instance());
		definitions.add(SecondaryTableAnnotationDefinition.instance());
		definitions.add(SecondaryTablesAnnotationDefinition.instance());
		definitions.add(SequenceGenerator2_0AnnotationDefinition.instance());
		definitions.add(TableAnnotationDefinition.instance());
		definitions.add(TableGeneratorAnnotationDefinition.instance());		
	}
	
	@Override
	protected void addTypeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(EmbeddableAnnotationDefinition.instance());
		definitions.add(EntityAnnotationDefinition.instance());
		definitions.add(MappedSuperclassAnnotationDefinition.instance());
	}
	
	@Override
	protected void addAttributeAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(Access2_0AnnotationDefinition.instance());
		definitions.add(AssociationOverride2_0AnnotationDefinition.instance());
		definitions.add(AssociationOverrides2_0AnnotationDefinition.instance());
		definitions.add(AttributeOverrideAnnotationDefinition.instance());
		definitions.add(AttributeOverridesAnnotationDefinition.instance());
		definitions.add(BasicAnnotationDefinition.instance());
		definitions.add(ColumnAnnotationDefinition.instance());
		definitions.add(EmbeddedAnnotationDefinition.instance());
		definitions.add(EmbeddedIdAnnotationDefinition.instance());
		definitions.add(EnumeratedAnnotationDefinition.instance());
		definitions.add(GeneratedValueAnnotationDefinition.instance());
		definitions.add(IdAnnotationDefinition.instance());
		definitions.add(JoinColumnAnnotationDefinition.instance());
		definitions.add(JoinColumnsAnnotationDefinition.instance());
		definitions.add(JoinTableAnnotationDefinition.instance());
		definitions.add(LobAnnotationDefinition.instance());
		definitions.add(ManyToManyAnnotationDefinition.instance());
		definitions.add(ManyToOneAnnotationDefinition.instance());
		definitions.add(MapKeyAnnotationDefinition.instance());
		definitions.add(OneToManyAnnotationDefinition.instance());
		definitions.add(OneToOneAnnotationDefinition.instance());
		definitions.add(OrderByAnnotationDefinition.instance());
		definitions.add(PrimaryKeyJoinColumnAnnotationDefinition.instance());
		definitions.add(PrimaryKeyJoinColumnsAnnotationDefinition.instance());
		definitions.add(SequenceGenerator2_0AnnotationDefinition.instance());
		definitions.add(TableGeneratorAnnotationDefinition.instance());
		definitions.add(TemporalAnnotationDefinition.instance());
		definitions.add(TransientAnnotationDefinition.instance());
		definitions.add(VersionAnnotationDefinition.instance());
	}
}
