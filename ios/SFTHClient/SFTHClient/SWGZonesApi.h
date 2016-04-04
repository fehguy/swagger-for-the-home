#import <Foundation/Foundation.h>
#import "SWGObject.h"
#import "SWGApiClient.h"


/**
 * NOTE: This class is auto generated by the swagger code generator program. 
 * https://github.com/swagger-api/swagger-codegen 
 * Do not edit the class manually.
 */

@interface SWGZonesApi: NSObject

@property(nonatomic, assign)SWGApiClient *apiClient;

-(instancetype) initWithApiClient:(SWGApiClient *)apiClient;
-(void) addHeader:(NSString*)value forKey:(NSString*)key;
-(unsigned long) requestQueueSize;
+(SWGZonesApi*) apiWithHeader:(NSString*)headerValue key:(NSString*)key;
+(SWGZonesApi*) sharedAPI;
///
///
/// 
/// 
///
/// 
///
/// @return NSArray* /* NSString */
-(NSNumber*) getZonesWithCompletionHandler: 
    (void (^)(NSArray* /* NSString */ output, NSError* error)) handler;


///
///
/// 
/// 
///
/// @param zoneId 
/// 
///
/// @return 
-(NSNumber*) quietZoneWithZoneId: (NSString*) zoneId
    completionHandler: (void (^)(NSError* error)) handler;



@end
