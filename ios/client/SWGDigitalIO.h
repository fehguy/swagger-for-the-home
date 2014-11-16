#import <Foundation/Foundation.h>
#import "SWGObject.h"
#import "SWGDate.h"


@interface SWGDigitalIO : SWGObject

@property(nonatomic) NSString* name;  

@property(nonatomic) SWGDate* timestamp;  

@property(nonatomic) NSNumber* position;  

@property(nonatomic) NSNumber* value;  

- (id) name: (NSString*) name
     timestamp: (SWGDate*) timestamp
     position: (NSNumber*) position
     value: (NSNumber*) value;

- (id) initWithValues: (NSDictionary*)dict;
- (NSDictionary*) asDictionary;


@end

