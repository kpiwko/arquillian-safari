package org.arquillian.safari.net

import org.arquillian.spacelift.task.Task

/**
 * This task will try to guess real IPv4 address of the machine. If it fails to get a single candidate,
 * it reverts back to 127.0.0.1 or value provided via chaining 
 * 
 * @author kpiwko
 *
 */
public class GuessRealIPTask extends Task<String, String> {

    private final String defaultIP = '127.0.0.1'

    /**
     * @param input Default IP address if guess was not successful
     */
    @Override
    protected String process(String input) throws Exception {

        List<String> candidates = new ArrayList<String>()
        NetworkInterface.getNetworkInterfaces().each { NetworkInterface netint ->
            String name = netint.getName()            
            if(name && !name.startsWith('virbr') && !name.startsWith('docker') && !name.startsWith('lo')) {
                netint.getInetAddresses().each { InetAddress inetAddress ->
                    String ip = inetAddress.getHostAddress()                    
                    if(!ip.contains(':')) {
                        candidates.add(ip)
                    }
                }
            }
        }
        if(candidates.size()==1) {
            if(input) {
                return input;
            }
            return defaultIP;
        }
        return candidates[0];
    }
}