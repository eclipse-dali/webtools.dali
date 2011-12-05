/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOneToManyRelationship2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToManyMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmOneToManyRelationship2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToMany;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkOneToManyMapping
	extends AbstractOrmOneToManyMapping<XmlOneToMany>
	implements
		EclipseLinkOneToManyMapping2_0,
		EclipseLinkOrmConvertibleMapping,
		OrmEclipseLinkConverterContainer.Owner
{
	protected final OrmEclipseLinkPrivateOwned privateOwned;

	protected final OrmEclipseLinkJoinFetch joinFetch;
	
	protected final OrmEclipseLinkConverterContainer converterContainer;


	public OrmEclipseLinkOneToManyMapping(OrmPersistentAttribute parent, XmlOneToMany xmlMapping) {
		super(parent, xmlMapping);
		this.privateOwned = new OrmEclipseLinkPrivateOwned(this);
		this.joinFetch = new OrmEclipseLinkJoinFetch(this);
		this.converterContainer = this.buildConverterContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.privateOwned.synchronizeWithResourceModel();
		this.joinFetch.synchronizeWithResourceModel();
		this.converterContainer.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.privateOwned.update();
		this.joinFetch.update();
		this.converterContainer.update();
	}


	// ********** private owned **********

	public EclipseLinkPrivateOwned getPrivateOwned() {
		return this.privateOwned;
	}


	// ********** join fetch **********

	public EclipseLinkJoinFetch getJoinFetch() {
		return this.joinFetch;
	}


	// ********** converters **********

	public OrmEclipseLinkConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected OrmEclipseLinkConverterContainer buildConverterContainer() {
		return new OrmEclipseLinkConverterContainerImpl(this, this, this.xmlAttributeMapping);
	}

	public int getNumberSupportedConverters() {
		return 1;
	}


	// ********** relationship **********

	@Override
	protected OrmOneToManyRelationship2_0 buildRelationship() {
		return new EclipseLinkOrmOneToManyRelationship(this);
	}

	@Override
	public EclipseLinkOrmOneToManyRelationship2_0 getRelationship() {
		return (EclipseLinkOrmOneToManyRelationship) super.getRelationship();
	}


	//************ refactoring ************

	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.converterContainer.createMoveTypeEdits(originalType, newPackage);
	}

	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.converterContainer.createRenamePackageEdits(originalPackage, newName);
	}

	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.converterContainer.createRenameTypeEdits(originalType, newName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// TODO - private owned, join fetch validation
	}
}
