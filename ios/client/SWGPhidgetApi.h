#import <Foundation/Foundation.h>
#import "SWGInputZone.h"
#import "SWGDigitalIO.h"
#import "SWGAnalogIO.h"



@interface SWGPhidgetApi: NSObject

-(void) addHeader:(NSString*)value forKey:(NSString*)key;
-(unsigned long) requestQueueSize;
+(SWGPhidgetApi*) apiWithHeader:(NSString*)headerValue key:(NSString*)key;
+(void) setBasePath:(NSString*)basePath;
+(NSString*) getBasePath;
/**

 returns all inputs
 
 */
-(NSNumber*) getAnalogInputsWithCompletionBlock :(void (^)(NSArray* output, NSError* error))completionBlock;

/**

 reads the input definitions based on configs
 
 */
-(NSNumber*) getInputZonesWithCompletionBlock :(void (^)(NSArray* output, NSError* error))completionBlock;

/**

 Updates the LCD
 
 @param msg 
 @param lineNumber 
 */
-(NSNumber*) setLcdWithCompletionBlock :(NSString*) msg 
        lineNumber:(NSNumber*) lineNumber 
        completionHandler: (void (^)(NSError* error))completionBlock;

/**

 sets all relays
 
 @param state 
 */
-(NSNumber*) setAllRelayOutputWithCompletionBlock :(NSNumber*) state 
        completionHandler: (void (^)(NSError* error))completionBlock;

/**

 gets an output state
 
 @param position 
 */
-(NSNumber*) getRelayOutputWithCompletionBlock :(NSNumber*) position 
        completionHandler: (void (^)(SWGDigitalIO* output, NSError* error))completionBlock;

/**

 sets a relay for a specific position
 
 @param state 
 @param position 
 */
-(NSNumber*) setOutputRelayWithCompletionBlock :(NSNumber*) state 
        position:(NSNumber*) position 
        completionHandler: (void (^)(SWGDigitalIO* output, NSError* error))completionBlock;

/**

 gets an output state
 
 @param state 
 @param name 
 */
-(NSNumber*) updateRelayZoneWithCompletionBlock :(NSNumber*) state 
        name:(NSString*) name 
        completionHandler: (void (^)(SWGDigitalIO* output, NSError* error))completionBlock;

/**

 Sets a relay with a timer
 
 @param name 
 @param timer 
 */
-(NSNumber*) relayOnWithTimerWithCompletionBlock :(NSString*) name 
        timer:(NSNumber*) timer 
        completionHandler: (void (^)(NSError* error))completionBlock;

@end
