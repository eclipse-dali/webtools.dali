/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.details.BasicComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddableComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddedComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddedIdComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EntityComposite;
import org.eclipse.jpt.ui.internal.mappings.details.IdComposite;
import org.eclipse.jpt.ui.internal.mappings.details.ManyToManyComposite;
import org.eclipse.jpt.ui.internal.mappings.details.ManyToOneComposite;
import org.eclipse.jpt.ui.internal.mappings.details.MappedSuperclassComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OneToManyComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OneToOneComposite;
import org.eclipse.jpt.ui.internal.mappings.details.TransientComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;


public abstract class BaseJpaUiFactory implements IJpaUiFactory
{

	public IJpaComposite createBasicMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new BasicComposite(parent, commandStack, widgetFactory);
	}

	public IJpaComposite createEmbeddableComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EmbeddableComposite(parent, commandStack, widgetFactory);
	}

	public IJpaComposite createEmbeddedIdMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EmbeddedIdComposite(parent, commandStack, widgetFactory);
	}

	public IJpaComposite createEmbeddedMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EmbeddedComposite(parent, commandStack, widgetFactory);
	}

	public IJpaComposite createEntityComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EntityComposite(parent, commandStack, widgetFactory);
	}

	public IJpaComposite createIdMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new IdComposite(parent, commandStack, widgetFactory);
	}

	public IJpaComposite createManyToManyMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new ManyToManyComposite(parent, commandStack, widgetFactory);
	}

	public IJpaComposite createManyToOneMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new ManyToOneComposite(parent, commandStack, widgetFactory);
	}

	public IJpaComposite createMappedSuperclassComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new MappedSuperclassComposite(parent, commandStack, widgetFactory);
	}

	public IJpaComposite createOneToManyMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new OneToManyComposite(parent, commandStack, widgetFactory);
	}

	public IJpaComposite createOneToOneMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new OneToOneComposite(parent, commandStack, widgetFactory);
	}

	public IJpaComposite createTransientMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new TransientComposite(parent, commandStack, widgetFactory);
	}

	public IJpaComposite createVersionMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new BasicComposite(parent, commandStack, widgetFactory);

	}
}