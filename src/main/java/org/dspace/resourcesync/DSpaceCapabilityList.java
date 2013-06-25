package org.dspace.resourcesync;

import org.dspace.core.Context;
import org.openarchives.resourcesync.CapabilityList;
import org.openarchives.resourcesync.ResourceSync;

import java.io.IOException;
import java.io.OutputStream;

public class DSpaceCapabilityList
{
    public void generate(Context context, OutputStream out, String describedBy,
                         String resourceList, String changeListArchive, String resourceDump,
                         String resourceSyncDescription, String changeList)
            throws IOException
    {
        CapabilityList cl = new CapabilityList(describedBy, null);
        if (resourceList != null)
        {
            cl.setResourceList(resourceList);
        }
        if (changeListArchive != null)
        {
            cl.setChangeListArchive(changeListArchive);
        }
        if (resourceDump != null)
        {
            cl.setResourceDump(resourceDump);
        }
        if (resourceSyncDescription != null)
        {
            cl.addLn(ResourceSync.REL_RESOURCESYNC, resourceSyncDescription);
        }
        if (changeList != null)
        {
            cl.setChangeList(changeList);
        }
        cl.serialise(out);
    }
}
