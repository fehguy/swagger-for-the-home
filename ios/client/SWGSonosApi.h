#import <Foundation/Foundation.h>



@interface SWGSonosApi: NSObject

-(void) addHeader:(NSString*)value forKey:(NSString*)key;
-(unsigned long) requestQueueSize;
+(SWGSonosApi*) apiWithHeader:(NSString*)headerValue key:(NSString*)key;
+(void) setBasePath:(NSString*)basePath;
+(NSString*) getBasePath;
/**

 Pauses all zones
 
 @param delay Delay before pausing
 */
-(NSNumber*) pauseAllWithCompletionBlock :(NSNumber*) delay 
        completionHandler: (void (^)(NSError* error))completionBlock;

/**

 Pauses all zones
 
 @param delay Delay before pausing
 */
-(NSNumber*) resumeAllWithCompletionBlock :(NSNumber*) delay 
        completionHandler: (void (^)(NSError* error))completionBlock;

@end
