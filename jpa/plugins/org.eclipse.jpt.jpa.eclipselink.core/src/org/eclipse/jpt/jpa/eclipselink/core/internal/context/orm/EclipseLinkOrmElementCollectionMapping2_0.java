/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.AbstractOrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlElementCollection;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkOrmElementCollectionMapping2_0
	extends AbstractOrmElementCollectionMapping2_0<XmlElementCollection>
	implements
		EclipseLinkElementCollectionMapping2_0,
		EclipseLinkOrmConvertibleMapping,
		OrmEclipseLinkConverterContainer.Owner
{
	protected final OrmEclipseLinkJoinFetch joinFetch;
	
	protected final OrmEclipseLinkConverterContainer converterContainer;


	public EclipseLinkOrmElementCollectionMapping2_0(OrmPersistentAttribute parent, XmlElementCollection resourceMapping) {
		super(parent, resourceMapping);
		this.joinFetch = new OrmEclipseLinkJoinFetch(this);
		this.converterContainer = this.buildConverterContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.joinFetch.synchronizeWithResourceModel();
		this.converterContainer.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.joinFetch.update();
		this.converterContainer.update();
	}


	// ********** join fetch **********

	public EclipseLinkJoinFetch getJoinFetch() {
		if (getJpaPlatformVersion().getVersion().equals(JptJpaEclipseLinkCorePlugin.ECLIPSELINK_PLATFORM_VERSION_2_0)) {
			throw new UnsupportedOperationException("join-fetch not supported in EclipseLink 2.0 platform"); //$NON-NLS-1$
		}
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
		return 2;
	}

	// ********** converter adapters **********

	/**
	 * put the EclipseLink convert adapter first - this is the order EclipseLink searches
	 */
	@Override
	protected Iterable<OrmConverter.Adapter> getConverterAdapters() {
		return new CompositeIterable<OrmConverter.Adapter>(
				OrmEclipseLinkConvert.Adapter.instance(),
				super.getConverterAdapters()
			);
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
		// TODO join fetch validation
	}
}
