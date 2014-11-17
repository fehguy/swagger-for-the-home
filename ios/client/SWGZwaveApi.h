#import <Foundation/Foundation.h>
#import "SWGDeviceState.h"
#import "SWGZWaveDevice.h"



@interface SWGZwaveApi: NSObject

-(void) addHeader:(NSString*)value forKey:(NSString*)key;
-(unsigned long) requestQueueSize;
+(SWGZwaveApi*) apiWithHeader:(NSString*)headerValue key:(NSString*)key;
+(void) setBasePath:(NSString*)basePath;
+(NSString*) getBasePath;
/**

 Gets state for a switch
 
 @param deviceId 
 */
-(NSNumber*) getSwitchStateWithCompletionBlock :(NSNumber*) deviceId 
        completionHandler: (void (^)(SWGDeviceState* output, NSError* error))completionBlock;

/**

 gets devices
 
 */
-(NSNumber*) getDevicesWithCompletionBlock :(void (^)(NSArray* output, NSError* error))completionBlock;

/**

 Updates
 
 @param deviceId 
 @param timer 
 */
-(NSNumber*) dimmerOnWithTimerWithCompletionBlock :(NSNumber*) deviceId 
        timer:(NSNumber*) timer 
        completionHandler: (void (^)(NSError* error))completionBlock;

/**

 Updates
 
 @param deviceId 
 @param value 
 */
-(NSNumber*) setDimmerValueWithCompletionBlock :(NSNumber*) deviceId 
        value:(NSNumber*) value 
        completionHandler: (void (^)(NSError* error))completionBlock;

/**

 Updates
 
 @param deviceId 
 @param timer 
 */
-(NSNumber*) setSwitchValueWithTimerWithCompletionBlock :(NSNumber*) deviceId 
        timer:(NSNumber*) timer 
        completionHandler: (void (^)(NSError* error))completionBlock;

/**

 Updates
 
 @param deviceId 
 @param value 
 */
-(NSNumber*) setSwitchValueWithCompletionBlock :(NSNumber*) deviceId 
        value:(NSNumber*) value 
        completionHandler: (void (^)(NSError* error))completionBlock;

@end
