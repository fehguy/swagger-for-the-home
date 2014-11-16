#import <Foundation/Foundation.h>
#import "SWGObject.h"


@interface SWGInputZone : SWGObject

@property(nonatomic) NSString* name;  

@property(nonatomic) NSNumber* logicalPosition;  

@property(nonatomic) NSNumber* position;  

@property(nonatomic) NSString* inputDeviceId;  

@property(nonatomic) NSString* outputDeviceId;  

- (id) name: (NSString*) name
     logicalPosition: (NSNumber*) logicalPosition
     position: (NSNumber*) position
     inputDeviceId: (NSString*) inputDeviceId
     outputDeviceId: (NSString*) outputDeviceId;

- (id) initWithValues: (NSDictionary*)dict;
- (NSDictionary*) asDictionary;


@end

