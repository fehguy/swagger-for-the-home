#import <Foundation/Foundation.h>
#import "SWGTemperatureForecast.h"



@interface SWGEnvironmentApi: NSObject

-(void) addHeader:(NSString*)value forKey:(NSString*)key;
-(unsigned long) requestQueueSize;
+(SWGEnvironmentApi*) apiWithHeader:(NSString*)headerValue key:(NSString*)key;
+(void) setBasePath:(NSString*)basePath;
+(NSString*) getBasePath;
/**

 Gets the forecast
 
 @param days Number of days to forecast
 */
-(NSNumber*) getForecastWithCompletionBlock :(NSNumber*) days 
        completionHandler: (void (^)(NSArray* output, NSError* error))completionBlock;

@end
