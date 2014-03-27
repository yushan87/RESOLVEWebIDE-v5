/**
 * This parses the location string passed in and returns the current base URL we are visiting.
 *
 * @param loc The full string web location path.
 * @returns {string} The base location URL.
 */
function getUrl(loc){
    var url = "";
    var pathname = loc.pathname;
    pathname = pathname.substring(0,pathname.lastIndexOf("/"));
    if(pathname.length == 0) {
        url = loc.host + (loc.pathname.length>1?loc.pathname+"/":loc.pathname);
    }
    else{
        url = loc.host + (loc.pathname.length>1?pathname+"/":loc.pathname);
    }
    return url;
}