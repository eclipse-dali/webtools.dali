/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaReadOnlyAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;

/**
 * Java attribute override container
 */
public class GenericJavaAssociationOverrideContainer
	extends AbstractJavaOverrideContainer<
			JavaAssociationOverrideContainer.Owner,
			JavaReadOnlyAssociationOverride,
			JavaAssociationOverride,
			JavaVirtualAssociationOverride,
			AssociationOverrideAnnotation
		>
	implements JavaAssociationOverrideContainer
{
	public GenericJavaAssociationOverrideContainer(JavaJpaContextNode parent, JavaAssociationOverrideContainer.Owner owner) {
		super(parent, owner);
	}


	public RelationshipMapping getRelationshipMapping(String attributeName) {
		return MappingTools.getRelationshipMapping(attributeName, this.owner.getOverridableTypeMapping());
	}

	public RelationshipReference resolveOverriddenRelationship(String associationOverrideName) {
		return this.owner.resolveOverriddenRelationship(associationOverrideName);
	}

	public JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, org.eclipse.jpt.core.context.JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.owner.buildJoinTableJoinColumnValidator(override, column, owner, textRangeResolver);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, org.eclipse.jpt.core.context.JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.owner.buildJoinTableInverseJoinColumnValidator(override, column, owner, textRangeResolver);
	}

	public JptValidator buildTableValidator(AssociationOverride override, Table table, TableTextRangeResolver textRangeResolver) {
		return this.owner.buildTableValidator(override, table, textRangeResolver);
	}

	@Override
	protected String getOverrideAnnotationName() {
		return AssociationOverrideAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected String getOverrideContainerAnnotationName() {
		return AssociationOverridesAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected JavaAssociationOverride buildSpecifiedOverride(AssociationOverrideAnnotation overrideAnnotation) {
		return this.getJpaFactory().buildJavaAssociationOverride(this, overrideAnnotation);
	}

	@Override
	protected void initializeSpecifiedOverride(JavaAssociationOverride specifiedOverride, JavaVirtualAssociationOverride virtualOverride) {
		specifiedOverride.initializeFromVirtual(virtualOverride);
	}

	@Override
	protected JavaVirtualAssociationOverride buildVirtualOverride(String name) {
		return this.getJpaFactory().buildJavaVirtualAssociationOverride(this, name);
	}
}
