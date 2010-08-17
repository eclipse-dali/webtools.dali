/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.internal.context.orm.AbstractEntityMappings;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkEntityMappingsImpl
	extends AbstractEntityMappings
	implements EclipseLinkEntityMappings
{

	protected final OrmEclipseLinkConverterHolder converterHolder;
	
	public EclipseLinkEntityMappingsImpl(OrmXml parent, XmlEntityMappings resource) {
		super(parent, resource);
		this.converterHolder = new OrmEclipseLinkConverterHolder(this, (XmlEntityMappings) this.xmlEntityMappings);
	}
	
	// **************** EclipseLinkEntityMappings impl **********************************

	public EclipseLinkConverterHolder getConverterHolder() {
		return this.converterHolder;
	}
	
	
	@Override
	public void update() {
		super.update();
		this.converterHolder.update();
	}


	// **************** refactoring *********************************************

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createRenameTypeEdits(originalType, newName),
			this.createConverterRenameTypeEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createConverterRenameTypeEdits(IType originalType, String newName) {
		return this.converterHolder.createRenameTypeEdits(originalType, newName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			super.createMoveTypeEdits(originalType, newPackage),
			this.createConverterMoveTypeEdits(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createConverterMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.converterHolder.createMoveTypeEdits(originalType, newPackage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createRenamePackageEdits(originalPackage, newName),
			this.createConverterRenamePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createConverterRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.converterHolder.createRenamePackageEdits(originalPackage, newName);
	}

	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.converterHolder.validate(messages, reporter);
	}
	
	@Override
	protected String latestDocumentVersion() {
		return getJpaPlatform().getMostRecentSupportedResourceType(
				JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE).getVersion();
	}
}
