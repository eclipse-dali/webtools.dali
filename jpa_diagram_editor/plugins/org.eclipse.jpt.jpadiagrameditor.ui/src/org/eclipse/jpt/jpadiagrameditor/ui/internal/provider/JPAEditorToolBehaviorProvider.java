/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.provider;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.impl.CreateContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.context.impl.DeleteContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.internal.features.context.impl.base.PictogramElementContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.palette.IPaletteCompartmentEntry;
import org.eclipse.graphiti.palette.IToolEntry;
import org.eclipse.graphiti.palette.impl.PaletteCompartmentEntry;
import org.eclipse.graphiti.platform.IPlatformImageConstants;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.tb.ContextButtonEntry;
import org.eclipse.graphiti.tb.ContextEntryHelper;
import org.eclipse.graphiti.tb.ContextMenuEntry;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IContextButtonEntry;
import org.eclipse.graphiti.tb.IContextButtonPadData;
import org.eclipse.graphiti.tb.IContextMenuEntry;
import org.eclipse.graphiti.tb.IDecorator;
import org.eclipse.graphiti.tb.ImageDecorator;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.facade.EclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddAllEntitiesFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ClickAddAttributeButtonFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ClickRemoveAttributeButtonFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CollapseAllEntitiesFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CollapseCompartmentShapeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CollapseEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.DeleteJPAEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.DiscardAndRemoveAllEntitiesFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ExpandAllEntitiesFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ExpandCompartmentShapeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ExpandEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.MoveEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.OpenJPADetailsViewFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.OpenMiniatureViewFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RefactorAttributeTypeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RemoveAndSaveEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RemoveJPAEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RenameEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RestoreEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.SaveAndRemoveAllEntitiesFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.SaveEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.ui.JPAEditorMatchingStrategy;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IEclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants.ShapeType;
import org.eclipse.ui.PartInitException;

@SuppressWarnings("restriction")
public class JPAEditorToolBehaviorProvider extends DefaultToolBehaviorProvider {

	private IEclipseFacade facade;

    public JPAEditorToolBehaviorProvider(IDiagramTypeProvider dtp) {
    	this(dtp, EclipseFacade.INSTANCE);
    }

    public JPAEditorToolBehaviorProvider(IDiagramTypeProvider dtp, IEclipseFacade eclipseFacade) {
        super(dtp);
        this.facade = eclipseFacade;
    }
    
    @Override
    public IContextButtonPadData getContextButtonPad(IPictogramElementContext context) {
        IContextButtonPadData data = super.getContextButtonPad(context);        
        ContainerShape cs = null;
        	PictogramElement pe = ((PictogramElementContext) context).getPictogramElement();
        	  Object ob = getFeatureProvider().getBusinessObjectForPictogramElement(pe);
      		if (ob == null){
      			return null;
           }
        	if (pe instanceof ContainerShape) {
            	cs = (ContainerShape)pe;
            	GraphicsAlgorithm ga = cs.getGraphicsAlgorithm();
            	if (ga instanceof Rectangle) {
            		List<GraphicsAlgorithm> gas = ((Rectangle)ga).getGraphicsAlgorithmChildren();
            		if ((gas.size() > 0) && (gas.get(0) instanceof Text)) {
                        setGenericContextButtons(data, pe, 0);            			
            			ClickRemoveAttributeButtonFeature feat = getConcreteFeatureProvider()
								.getClickRemoveAttributeButtonFeature();
            			DeleteContext delCtx = new DeleteContext(cs);
                        ContextButtonEntry btn =  new ContextButtonEntry(feat, delCtx);
                        btn.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteAttributeButtonlabel); 
                        btn.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteAttributeButtonDescription); 
                        btn.setIconId(IPlatformImageConstants.IMG_EDIT_DELETE);
                        data.getDomainSpecificContextButtons().add(btn);    
                        return data; 
            		}
            	}        	                    		
        	} else {
        		return data;
        	}
        	
		setGenericContextButtons(data, pe, CONTEXT_BUTTON_DELETE
				| CONTEXT_BUTTON_REMOVE);
		List<IContextButtonEntry> buttons = data.getGenericContextButtons();
		Iterator<IContextButtonEntry> btnsIt = buttons.iterator();
		while (btnsIt.hasNext()) {
			IContextButtonEntry button = btnsIt.next();
			if (button.getFeature() instanceof RemoveJPAEntityFeature) {
				button.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeEntityFromDiagramButtonLabel);
				button.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeEntityFromDiagramButtonDescription);
			} else if (button.getFeature() instanceof DeleteJPAEntityFeature) {
				button.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteEntityFromModelButtonLabel);
				button.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteEntityFromModelButtonDescription);
			}
		}

		ContextButtonEntry button = null;
		if (context instanceof PictogramElementContext) {

			ClickAddAttributeButtonFeature feature = getConcreteFeatureProvider().getClickAddAttributeButtonFeature();
			CreateContext createCtx = new CreateContext();
			createCtx.setTargetContainer(cs);
			button = new ContextButtonEntry(feature, createCtx);
			button.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_createAttributeButtonlabel);
			button.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_createAttributeButtonDescription);
			button.setIconId(JPAEditorImageProvider.ADD_ATTRIBUTE);
			data.getDomainSpecificContextButtons().add(button);

			PictogramElementContext c = (PictogramElementContext) context;
			RemoveAndSaveEntityFeature ft2 = new RemoveAndSaveEntityFeature(getFeatureProvider());
			RemoveContext remCtx = new RemoveContext(c.getPictogramElement());
			button = new ContextButtonEntry(ft2, remCtx);
			button.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAndSaveButtonText);
			button.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAndSaveButtonDescription);
			button.setIconId(JPAEditorImageProvider.ICON_SAVE_AND_REMOVE);
			data.getDomainSpecificContextButtons().add(button);

			SaveEntityFeature ft = new SaveEntityFeature(getConcreteFeatureProvider());
			CustomContext ctx = new CustomContext();
			ctx.setPictogramElements(new PictogramElement[] { c.getPictogramElement() });
			button = new ContextButtonEntry(ft, ctx);
			button.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_saveButtonText);
			button.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_saveButtonDescription);
			button.setIconId(JPAEditorImageProvider.ICON_SAVE);
			data.getDomainSpecificContextButtons().add(button);

			RestoreEntityFeature ft1 = new RestoreEntityFeature(getConcreteFeatureProvider());
			ctx = new CustomContext();
			ctx.setPictogramElements(new PictogramElement[] { c.getPictogramElement() });
			button = new ContextButtonEntry(ft1, ctx);
			button.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_restoreButtonText);
			button.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_restoreButtonDescription);
			button.setIconId(JPAEditorImageProvider.ICON_RESTORE);
			data.getDomainSpecificContextButtons().add(button);

			ctx = new CustomContext(new PictogramElement[] { pe });
			IFeatureProvider featureProvider = getFeatureProvider();
			ICustomFeature[] customFeatures = featureProvider.getCustomFeatures(ctx);
			int featureCount = customFeatures.length;
			for (int i = 0; i < featureCount; i++) {
				ICustomFeature customFeature = customFeatures[i];
				if (customFeature.isAvailable(ctx)) {
					if ((customFeature instanceof CollapseEntityFeature)) {
						IContextButtonEntry contextButton = ContextEntryHelper
								.createCollapseContextButton(true, customFeature, ctx);
						data.setCollapseContextButton(contextButton);
					} else if ((customFeature instanceof ExpandEntityFeature)) {
						IContextButtonEntry contextButton = ContextEntryHelper
								.createCollapseContextButton(false,	customFeature, ctx);
						data.setCollapseContextButton(contextButton);
					}
				}
			}
		}
        
        return data;
    }

    
    @Override
    public IPaletteCompartmentEntry[] getPalette() {
        List<IPaletteCompartmentEntry> ret =
            new ArrayList<IPaletteCompartmentEntry>();
        IPaletteCompartmentEntry[] superCompartments =
            super.getPalette();
        for (int i = 0; i < superCompartments.length; i++) {
        	String newLabel = (superCompartments[i].getLabel().equals("Objects") ? //$NON-NLS-1$
        			JPAEditorMessages.PaletteCompartment_Objects : 
        				JPAEditorMessages.PaletteCompartment_Connections); 
        	PaletteCompartmentEntry entry = new PaletteCompartmentEntry(newLabel, superCompartments[i].getIconId());       
        	List<IToolEntry> lst = superCompartments[i].getToolEntries();
        	Iterator<IToolEntry> it = lst.iterator();
        	while(it.hasNext()) {
        		entry.addToolEntry(it.next());
        	}
        	ret.add(entry);
        }
        IPaletteCompartmentEntry en = ret.get(0);
        ret.set(0, ret.get(1));
        ret.set(1, en);
        return ret.toArray(new IPaletteCompartmentEntry[ret.size()]);
    }
   
    @Override
    public IDecorator[] getDecorators(PictogramElement pe) {
		IFeatureProvider featureProvider = getFeatureProvider();
		Object bo = featureProvider.getBusinessObjectForPictogramElement(pe);

		if (bo instanceof JavaPersistentType) {
			JavaPersistentType persistentType = (JavaPersistentType) bo;
			IFile file = (IFile) persistentType.getResource();
			if(!file.exists()){
				return new IDecorator[0];
			}
			IMarker[] markers = new IMarker[0];
			try {
				markers = file.findMarkers(null, true, IResource.DEPTH_INFINITE);
				IDecorator[] result = new IDecorator[1];
				if (markers.length == 0)
					return super.getDecorators(pe);
				IMarker marker = getHighestPrioMarker(markers);
				ImageDecorator imageRenderingDecorator = createDecoratorFromMarker(marker);
				if (imageRenderingDecorator != null) {
					imageRenderingDecorator.setX(5 * (pe.getGraphicsAlgorithm().getWidth() / 6));
					imageRenderingDecorator.setY(5 * (pe.getGraphicsAlgorithm().getHeight() / 6));
					result[0] = imageRenderingDecorator;
				}
				if (result[0] != null)
					return result;
			} catch (CoreException e) {
				JPADiagramEditorPlugin.getDefault().getLog().log(e.getStatus());
			}
		}
		return super.getDecorators(pe);
	}

	private IMarker getHighestPrioMarker(IMarker[] markers) {
		IMarker result = markers[0];
		for (IMarker marker : markers) {
			try {
				final Integer resultSeverity = (Integer) result.getAttribute(IMarker.SEVERITY);
				if (resultSeverity == null)
					continue;
				if (IMarker.SEVERITY_ERROR == resultSeverity)
					// return the first error marker
					break;
				Integer markerSeverity = (Integer) marker.getAttribute(IMarker.SEVERITY);
				if ((markerSeverity != null) && (markerSeverity > resultSeverity))
					result = marker;
			} catch (CoreException e) {
				JPADiagramEditorPlugin.getDefault().getLog().log(e.getStatus());
				continue;
			}
		}
		return result;
	}

	private ImageDecorator createDecoratorFromMarker(IMarker marker) {
		try {
			Integer severity = (Integer) marker.getAttribute(IMarker.SEVERITY);
			String decoratorType = IPlatformImageConstants.IMG_ECLIPSE_INFORMATION_TSK;
			if (severity != null) {
				switch (severity) {
				case 0:
					decoratorType = IPlatformImageConstants.IMG_ECLIPSE_INFORMATION_TSK;
				case 1:
					decoratorType = IPlatformImageConstants.IMG_ECLIPSE_WARNING_TSK;
					break;
				case 2:
					decoratorType = IPlatformImageConstants.IMG_ECLIPSE_ERROR_TSK;
					break;
				}
				ImageDecorator imageRenderingDecorator = new ImageDecorator(decoratorType);
				imageRenderingDecorator.setMessage((String) marker.getAttribute(IMarker.MESSAGE));
				return imageRenderingDecorator;
			}
			return null;
		} catch (CoreException e) {
			JPADiagramEditorPlugin.getDefault().getLog().log(e.getStatus());
			return null;
		}
	}
	
    public String getToolTip(GraphicsAlgorithm ga) {
    	PictogramElement pe = ga.getPictogramElement();
        if (!(ga instanceof Rectangle)) 
        	return null;
        List<GraphicsAlgorithm> ch = ga.getGraphicsAlgorithmChildren();
        if ((ch == null) || (ch.size() == 0)) 
        	return null;
        ga = ch.get(0);
        if (!(ga instanceof Text)) 
        	return null;
        if(((Text)ga).getFont().isItalic()){
    		return getToolTipForCompartmentShape(ga);
    	    }
        
        Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if (bo != null) {
			if (bo instanceof JavaPersistentType) {
				String superPersistentTypeName = null;
				if (((JavaPersistentType) bo).getSuperPersistentType() != null) {
					superPersistentTypeName = ((JavaPersistentType) bo).getSuperPersistentType().getName();
				}
				return JPAEditorUtil.getTooltipText((JavaPersistentType)bo, superPersistentTypeName);
			} else {
				if (bo instanceof JavaPersistentAttribute) {
					return JPAEditorUtil.getTooltipText((JavaPersistentAttribute)bo);
				}
			}
		}
        if (!(pe instanceof Shape)) 
        	return null;
        Shape sh = (Shape)pe;
        ContainerShape csh = sh.getContainer();
        if (csh == null) 
        	return null; 
        bo = getFeatureProvider().getBusinessObjectForPictogramElement(csh);
		if (bo instanceof JavaPersistentType) {
			String superPersistentTypeName = null;
			if (((JavaPersistentType) bo).getSuperPersistentType() != null) {
				superPersistentTypeName = ((JavaPersistentType) bo)
						.getSuperPersistentType().getName();
			}
			return JPAEditorUtil.getTooltipText((JavaPersistentType) bo,
					superPersistentTypeName);
		}
        return null;
    }

	private String getToolTipForCompartmentShape(GraphicsAlgorithm ga) {
		Text txt = (Text) ga;
		ContainerShape shape = (ContainerShape) Graphiti.getPeService().getActiveContainerPe(ga);
			if(GraphicsUpdater.isCollapsed(shape))
				return MessageFormat.format(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandCompartToolTip, txt.getValue());
			return MessageFormat.format(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseCompartToolTip, txt.getValue());
	}

	protected JPAEditorFeatureProvider getConcreteFeatureProvider() {
		return (JPAEditorFeatureProvider)super.getFeatureProvider();		
	}
	
    @Override
    public IContextMenuEntry[] getContextMenu(ICustomContext context) {
    	ICustomContext customContext = (ICustomContext)context;
    	PictogramElement[] pictEls = customContext.getPictogramElements();
    	
    	ICustomFeature saveEntityFeature = new SaveEntityFeature(getFeatureProvider());
		ContextMenuEntry saveEntityMenuItem = new ContextMenuEntry(saveEntityFeature, context);
		saveEntityMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_saveMenuItem);
		saveEntityMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_saveMenuItemDescr);
		saveEntityMenuItem.setSubmenu(false);
		
		ICustomFeature restoreEntityFeature = new RestoreEntityFeature(getFeatureProvider());
		ContextMenuEntry restoreEntityMenuItem = new ContextMenuEntry(restoreEntityFeature, context);
		restoreEntityMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_discardChangesMenuItem);
		restoreEntityMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_discardChangesMenuItemDescr);
		restoreEntityMenuItem.setSubmenu(false);		
    	
		ICustomFeature collapseFeature = new CollapseEntityFeature(getFeatureProvider());
		ContextMenuEntry collapseEntityMenuItem = new ContextMenuEntry(collapseFeature, context);
		collapseEntityMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseEntityMenuItem);
		collapseEntityMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseEntityMenuItemDescr);
		collapseEntityMenuItem.setSubmenu(false);
		
		ICustomFeature collapseAllFeature = new CollapseAllEntitiesFeature(getFeatureProvider());
		ContextMenuEntry collapseAllMenuItem = new ContextMenuEntry(collapseAllFeature, context);
		collapseAllMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseAllEntitiesMenuItem);
		collapseAllMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseAllEntitiesMenuItemDescr);
		collapseAllMenuItem.setSubmenu(false);		

		ICustomFeature expandFeature = new ExpandEntityFeature(getFeatureProvider());
		ContextMenuEntry expandEntityMenuItem= new ContextMenuEntry(expandFeature, context);
		expandEntityMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandEntityMenuItem);
		expandEntityMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandEntitymenuItemDescr);
		expandEntityMenuItem.setSubmenu(false);
		
		ICustomFeature expandAllFeature = new ExpandAllEntitiesFeature(getFeatureProvider());
		ContextMenuEntry expandAllMenuItem= new ContextMenuEntry(expandAllFeature, context);
		expandAllMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandAllEntitiesMenuItem);
		expandAllMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandAllEntitiesMenuItemDescr);
		expandAllMenuItem.setSubmenu(false);		

        ICustomFeature openJPADetailsViewFeature = new OpenJPADetailsViewFeature(getFeatureProvider());
        ContextMenuEntry openJPADetailsViewMenuItem = new ContextMenuEntry(openJPADetailsViewFeature, context);
        openJPADetailsViewMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_openJPADetailsView);
        openJPADetailsViewMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_openJPADetailsViewDesc);
        openJPADetailsViewMenuItem.setSubmenu(false);
        
        ICustomFeature openMiniatureViewFeature = new OpenMiniatureViewFeature(getFeatureProvider());
        ContextMenuEntry openMiniatureViewMenuItem = new ContextMenuEntry(openMiniatureViewFeature, context);
        openMiniatureViewMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_openMiniatureView);
        openMiniatureViewMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_openMiniatureViewDesc);
        openMiniatureViewMenuItem.setSubmenu(false);
        
        boolean isEmpty = true;
        ContextMenuEntry removeAllEntitiesSubmenu = null;
        if(getDiagramTypeProvider().getDiagram().getChildren().size()!=0){
        isEmpty = false;
        removeAllEntitiesSubmenu = new ContextMenuEntry(null, null);
        removeAllEntitiesSubmenu.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAllEntitiesMenu);
        removeAllEntitiesSubmenu.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAllEntitiesMenu);
        removeAllEntitiesSubmenu.setSubmenu(true);
		
        ICustomFeature customFeature = new SaveAndRemoveAllEntitiesFeature(getFeatureProvider());    	
        ContextMenuEntry saveAndRemovEntityMenuItem = new ContextMenuEntry(customFeature, context);
        saveAndRemovEntityMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAndSaveAllEntitiesAction);
        saveAndRemovEntityMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAndSaveAllEntitiesAction);
        saveAndRemovEntityMenuItem.setSubmenu(false);
        
		customFeature = new DiscardAndRemoveAllEntitiesFeature(getFeatureProvider());    	
        ContextMenuEntry discardAndRemovEntityMenuItem = new ContextMenuEntry(customFeature, context);
        discardAndRemovEntityMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAndDiscardAllEntitiesAction);
        discardAndRemovEntityMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAndDiscardAllEntitiesAction);
        discardAndRemovEntityMenuItem.setSubmenu(false);
                 
        removeAllEntitiesSubmenu.add(saveAndRemovEntityMenuItem);
        removeAllEntitiesSubmenu.add(discardAndRemovEntityMenuItem);
        
        }
                

    	if ((pictEls == null) || (pictEls.length == 0)) 
    		return new IContextMenuEntry[] { openJPADetailsViewMenuItem };
    	if (pictEls[0] instanceof ContainerShape) {
    		Object ob = getFeatureProvider().getBusinessObjectForPictogramElement(pictEls[0]);
            if (pictEls[0] instanceof Diagram) {
            	ICustomFeature customFeature = new AddAllEntitiesFeature(getFeatureProvider());    	
                ContextMenuEntry showAllEntsMenuItem = new ContextMenuEntry(customFeature, context);
                showAllEntsMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_showAllTheEntities);
                showAllEntsMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_showAllTheEntitiesDesc);
                showAllEntsMenuItem.setSubmenu(false);
                if(isEmpty){
                	return new IContextMenuEntry[] { showAllEntsMenuItem,
                			 collapseAllMenuItem,
                			 expandAllMenuItem,
							 openJPADetailsViewMenuItem,
							 openMiniatureViewMenuItem};
                } else {
                return new IContextMenuEntry[] { showAllEntsMenuItem, 
                								 removeAllEntitiesSubmenu,
                								 collapseAllMenuItem,
                								 expandAllMenuItem,
                								 openJPADetailsViewMenuItem,
                								 openMiniatureViewMenuItem};
                }
        	}    
            if((ob == null) && (pictEls[0].getGraphicsAlgorithm() instanceof Rectangle)){
            	ICustomFeature collapseCompartmentFeature = new CollapseCompartmentShapeFeature(getFeatureProvider());
        		ContextMenuEntry collapseCompartmentMenuItem = new ContextMenuEntry(collapseCompartmentFeature, context);
        		collapseCompartmentMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseAttrGroupMenuItem);
        		collapseCompartmentMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseAttrGroupMenuItemDescr);
        		collapseCompartmentMenuItem.setSubmenu(false);

        		ICustomFeature expandCompartmentFeature = new ExpandCompartmentShapeFeature(getFeatureProvider());
        		ContextMenuEntry expandCompartmentMenuItem= new ContextMenuEntry(expandCompartmentFeature, context);
        		expandCompartmentMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandAttrMenuItem);
        		expandCompartmentMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandAttrMenuItemDescr);
        		expandCompartmentMenuItem.setSubmenu(false);
        		return new IContextMenuEntry[] {collapseCompartmentMenuItem, expandCompartmentMenuItem};
            }
            
            /*
            //Apply Pattern menu
            
            ICustomFeature applyPatternFeature = new ApplyPatternFeature(getFeatureProvider());
            ContextMenuEntry applyPatternMenuItem = new ContextMenuEntry(applyPatternFeature, context);
            applyPatternMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_applyPattern);
            applyPatternMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_applyPatternDesc);
            applyPatternMenuItem.setSubmenu(false);
            //Apply Pattern menu
             */
            
            ContextMenuEntry refactorClassSubmenu = new ContextMenuEntry(null, null);
            refactorClassSubmenu.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_refactorSubMenu);
            refactorClassSubmenu.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_refactorSubMenu);
            refactorClassSubmenu.setSubmenu(true);
            
    		
            ICustomFeature customFeature = new RenameEntityFeature(getFeatureProvider());    	
            ContextMenuEntry renameEntityMenuItem = new ContextMenuEntry(customFeature, context);
            renameEntityMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_renameEntityClass);
            renameEntityMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_renameEntityClass);
            renameEntityMenuItem.setSubmenu(false);
            
    		customFeature = new MoveEntityFeature(getFeatureProvider());    	
            ContextMenuEntry moveEntityMenuItem = new ContextMenuEntry(customFeature, context);
            moveEntityMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_moveEntityClass);
            moveEntityMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_moveEntityClass);
            moveEntityMenuItem.setSubmenu(false);            
                     
            refactorClassSubmenu.add(renameEntityMenuItem);
            refactorClassSubmenu.add(moveEntityMenuItem);  
            
            String shapeType = Graphiti.getPeService().getPropertyValue(pictEls[0], JPAEditorConstants.PROP_SHAPE_TYPE);
            
            if ((shapeType == null) || !shapeType.equals(ShapeType.ATTRIBUTE.toString())) 
            	return new IContextMenuEntry[] { saveEntityMenuItem,
            		                             refactorClassSubmenu,
            		                             collapseEntityMenuItem, 
            		                             collapseAllMenuItem,
            		                             expandEntityMenuItem,
            		                             expandAllMenuItem,
            		                             restoreEntityMenuItem,
            									 //applyPatternMenuItem, 
            									 removeAllEntitiesSubmenu,
            									 openJPADetailsViewMenuItem,
            									 openMiniatureViewMenuItem};
            
    		customFeature = new RefactorAttributeTypeFeature(getFeatureProvider());     		
            ContextMenuEntry refactorAttributeTypeMenuItem = new ContextMenuEntry(customFeature, context);
            refactorAttributeTypeMenuItem.setText(JPAEditorMessages.JPAEditorToolBehaviorProvider_refactorAttributeType);
            refactorAttributeTypeMenuItem.setDescription(JPAEditorMessages.JPAEditorToolBehaviorProvider_refactorAttributeTypeDesc);
            refactorAttributeTypeMenuItem.setSubmenu(false);                   

        	return new IContextMenuEntry[] { refactorClassSubmenu, 
        									 refactorAttributeTypeMenuItem, 
        									 collapseAllMenuItem,
        									 expandAllMenuItem,
        									 //applyPatternMenuItem, 
        									 openJPADetailsViewMenuItem,
        									 openMiniatureViewMenuItem};
    	}
    	return new IContextMenuEntry[] { removeAllEntitiesSubmenu,
    									 collapseAllMenuItem,
    									 expandAllMenuItem, 
    									 openJPADetailsViewMenuItem,
    									 openMiniatureViewMenuItem};
    }

	@Override
	public ICustomFeature getDoubleClickFeature(IDoubleClickContext context) {
		PictogramElement pe = context.getPictogramElements()[0];
		Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(
				pe);
		if (bo instanceof JavaPersistentAttribute) {
			JavaPersistentAttribute jpa = (JavaPersistentAttribute) bo;
			IFile file = (IFile) jpa.getResource();
			try {
				file.setSessionProperty(new QualifiedName(null, JPAEditorMatchingStrategy.DOUBLE_CLICK), "true");	//$NON-NLS-1$			
				facade.getIDE().openEditor(file);
			} catch (PartInitException e) {
				System.err.println("Cannot open editor");	//$NON-NLS-1$
				e.printStackTrace();				
			} catch (CoreException e) {
				System.err.println("Cannot open editor");	//$NON-NLS-1$
				e.printStackTrace();				
			}
		}
		if (bo instanceof JavaPersistentType) {
			JavaPersistentType jpt = (JavaPersistentType) bo;
			IFile file = (IFile) jpt.getResource();
			try {
				file.setSessionProperty(new QualifiedName(null, JPAEditorMatchingStrategy.DOUBLE_CLICK), "true");	//$NON-NLS-1$
				facade.getIDE().openEditor(file);
			} catch (PartInitException e) {
				System.err.println("Cannot open editor");	//$NON-NLS-1$
				e.printStackTrace();				
			} catch (CoreException e) {
				System.err.println("Cannot open editor");	//$NON-NLS-1$
				e.printStackTrace();				
			}
		}
		if ((bo == null) && (pe.getGraphicsAlgorithm() instanceof Rectangle)) {
			ICustomContext cont = new CustomContext(
					new PictogramElement[] { pe });
			ContainerShape containerShape = (ContainerShape) pe;
			if (GraphicsUpdater.isCollapsed(containerShape)) {
				ExpandCompartmentShapeFeature feature = new ExpandCompartmentShapeFeature(
						getFeatureProvider());
				feature.execute(cont);
			} else {
				CollapseCompartmentShapeFeature feature = new CollapseCompartmentShapeFeature(
						getFeatureProvider());
				feature.execute(cont);
			}

		}
        return super.getDoubleClickFeature(context);
	}   	
	
}
