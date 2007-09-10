/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Use IJpaFactory to create any IJavaTypeMapping or IJavaAttributeMappings.  This is necessary
 * so that platforms can extend the java model with their own annotations. 
 * IJavaTypeMappingProvider and IJavaAttributeMappingProvider use this factory.
 * See IJpaPlatform.javaTypeMappingProviders() and IJpaPlatform.javaAttributeMappingProviders()
 * for creating new mappings types.
 * @see BaseJpaUiFactory
 */
public interface IJpaUiFactory
{
	IJpaComposite createEntityComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
	
	IJpaComposite createEmbeddableComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
	
	IJpaComposite createMappedSuperclassComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
	
	IJpaComposite createBasicMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
	
	IJpaComposite createEmbeddedMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
	
	IJpaComposite createEmbeddedIdMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
	
	IJpaComposite createIdMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
	
	IJpaComposite createManyToManyMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
	
	IJpaComposite createManyToOneMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
	
	IJpaComposite createOneToManyMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
	
	IJpaComposite createOneToOneMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
	
	IJpaComposite createTransientMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
	
	IJpaComposite createVersionMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory);
}
