package org.eclipse.jpt.ui;

import static org.eclipse.jpt.ui.JptUiPlugin.PLUGIN_ID;
import static org.eclipse.ui.plugin.AbstractUIPlugin.imageDescriptorFromPlugin;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public final class CommonImages
{
    public static final ImageDescriptor DESC_OVERLAY_ERROR
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/overlays/error.gif" ); //$NON-NLS-1$

    public static final ImageDescriptor DESC_OVERLAY_WARNING
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/overlays/warning.png" ); //$NON-NLS-1$

    public static final ImageDescriptor DESC_BUTTON_ADD 
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/add.png" ); //$NON-NLS-1$
    
    public static final ImageDescriptor DESC_BUTTON_EDIT 
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/edit.png" ); //$NON-NLS-1$
    
    public static final ImageDescriptor DESC_BUTTON_DELETE 
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/delete.png" ); //$NON-NLS-1$
    
    public static final ImageDescriptor DESC_BUTTON_MOVE_UP 
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/move-up.png" ); //$NON-NLS-1$
    
    public static final ImageDescriptor DESC_BUTTON_MOVE_DOWN 
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/move-down.png" ); //$NON-NLS-1$
    
    public static final ImageDescriptor DESC_BUTTON_EXPAND_ALL 
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/expand-all.png" ); //$NON-NLS-1$
    
    public static final ImageDescriptor DESC_BUTTON_COLLAPSE_ALL 
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/collapse-all.png" ); //$NON-NLS-1$
    
    public static final ImageDescriptor DESC_BUTTON_RESTORE_DEFAULTS 
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/restore-defaults.png" ); //$NON-NLS-1$
    
    public static final ImageDescriptor DESC_BUTTON_BROWSE
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/browse.png" ); //$NON-NLS-1$
    
    public static final ImageDescriptor DESC_BUTTON_BROWSE_MINI
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/browse-mini.png" ); //$NON-NLS-1$
    
    public static final ImageDescriptor DESC_BUTTON_SELECT_ALL
    	= imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/select-all.png" ); //$NON-NLS-1$

    public static final ImageDescriptor DESC_BUTTON_DESELECT_ALL
		= imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/deselect-all.png" ); //$NON-NLS-1$

    public static final ImageDescriptor DESC_OBJECT_FILE
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/objects/file.png" ); //$NON-NLS-1$

    public static final ImageDescriptor DESC_OBJECT_FOLDER
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/objects/folder.png" ); //$NON-NLS-1$
    
    public static final ImageDescriptor DESC_OBJECT_PACKAGE
        = imageDescriptorFromPlugin( PLUGIN_ID, "images/objects/package.png" ); //$NON-NLS-1$

    private static Map<ImageDescriptor,Image> cache = new HashMap<ImageDescriptor,Image>();

	public static ImageDescriptor ADD_CONNECTION_IMAGE
    	= imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/add-connection.gif" ); //$NON-NLS-1$

	public static ImageDescriptor RECONNECT_IMAGE
		= imageDescriptorFromPlugin( PLUGIN_ID, "images/buttons/reconnect.png" ); //$NON-NLS-1$

	public static ImageDescriptor TABLE_IMAGE
		= imageDescriptorFromPlugin( PLUGIN_ID, "images/objects/table.gif" ); //$NON-NLS-1$
    
	public static ImageDescriptor TABLE_OBJ_IMAGE
		= imageDescriptorFromPlugin( PLUGIN_ID, "images/objects/table_obj.gif" ); //$NON-NLS-1$

	public static ImageDescriptor COLUMN_IMAGE
		= imageDescriptorFromPlugin( PLUGIN_ID, "images/objects/column.gif" ); //$NON-NLS-1$
	
	public static ImageDescriptor COLUMN_KEY_IMAGE
		= imageDescriptorFromPlugin( PLUGIN_ID, "images/objects/columnKey.gif" ); //$NON-NLS-1$
	
    public static Image createImage( final ImageDescriptor descriptor )
    {
        synchronized( cache )
        {
            Image image = cache.get( descriptor );
            
            if( image == null )
            {
                image = descriptor.createImage();
                cache.put( descriptor, image );
            }
            
            return image;
        }
    }
    
}
